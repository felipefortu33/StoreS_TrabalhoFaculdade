package produtos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BancoDeDados.DBConnection;

public class ProdutoDAO {

    // Método para cadastrar um produto no banco de dados
    public void cadastrarProduto(Produto produto) {
        String sql = "INSERT INTO produtos (nome_produto, preco, quantidade) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidade());

            stmt.executeUpdate();
            System.out.println("Produto cadastrado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para listar todos os produtos no banco de dados
    public List<Produto> listarProdutos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto produto = new Produto(
                    rs.getString("nome_produto"),
                    rs.getDouble("preco"),
                    rs.getInt("quantidade")
                );
                produto.setId(rs.getInt("id"));
                produtos.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }

    // Método para editar um produto no banco de dados
    public void editarProduto(Produto produto) {
        String sql = "UPDATE produtos SET nome_produto = ?, preco = ?, quantidade = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidade());
            stmt.setInt(4, produto.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Produto atualizado com sucesso!");
            } else {
                System.out.println("Produto não encontrado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para remover um produto do banco de dados
    public void removerProduto(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Produto removido com sucesso!");
            } else {
                System.out.println("Produto não encontrado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para buscar um produto pelo nome
    public Produto buscarProdutoPorNome(String nome) {
        String sql = "SELECT * FROM produtos WHERE nome_produto = ?";
        Produto produto = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    produto = new Produto(
                        rs.getString("nome_produto"),
                        rs.getDouble("preco"),
                        rs.getInt("quantidade")
                    );
                    produto.setId(rs.getInt("id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produto;
    }

    // Método para buscar um produto pelo ID
    public Produto buscarProdutoPorId(int id) {
        String sql = "SELECT * FROM produtos WHERE id = ?";
        Produto produto = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    produto = new Produto(
                        rs.getString("nome_produto"),
                        rs.getDouble("preco"),
                        rs.getInt("quantidade")
                    );
                    produto.setId(rs.getInt("id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produto;
    }

    // Método para atualizar o estoque após uma venda
    public boolean atualizarEstoqueAposVenda(int id, int quantidadeVendida, double precoUnitario) {
        String sql = "UPDATE produtos SET quantidade = quantidade - ? "
                   + "WHERE id = ? AND quantidade >= ?";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            stmt.setInt(1, quantidadeVendida);
            stmt.setInt(2, id);
            stmt.setInt(3, quantidadeVendida);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                conn.rollback();
                return false;
            }

            registrarMovimentacao(conn, id, "VENDA", quantidadeVendida,
                precoUnitario, precoUnitario * quantidadeVendida);
            conn.commit();
            return true;
            }

        } catch (SQLException e) {
            rollback(conn);
            e.printStackTrace();
            return false;
        } finally {
            close(conn);
        }
    }

    public boolean atualizarEstoqueAposCompra(int id, double novoPreco, int quantidadeComprada) {
        String sql = "UPDATE produtos SET preco = ?, quantidade = quantidade + ? WHERE id = ?";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            stmt.setDouble(1, novoPreco);
            stmt.setInt(2, quantidadeComprada);
            stmt.setInt(3, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                conn.rollback();
                return false;
            }

            registrarMovimentacao(conn, id, "COMPRA", quantidadeComprada,
                novoPreco, novoPreco * quantidadeComprada);
            conn.commit();
            return true;
            }
        } catch (SQLException e) {
            rollback(conn);
            e.printStackTrace();
            return false;
        } finally {
            close(conn);
        }
    }

    private void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ignored) {
                // Mantem o erro original da operacao.
            }
        }
    }

    private void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ignored) {
                // A operacao ja foi concluida ou desfeita.
            }
        }
    }

    private void registrarMovimentacao(Connection conn, int produtoId, String tipo,
                                       int quantidade, double precoUnitario,
                                       double valorTotal) throws SQLException {
        String sql = "INSERT INTO movimentacoes_estoque "
                   + "(produto_id, tipo, quantidade, preco_unitario, valor_total) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, produtoId);
            stmt.setString(2, tipo);
            stmt.setInt(3, quantidade);
            stmt.setDouble(4, precoUnitario);
            stmt.setDouble(5, valorTotal);
            stmt.executeUpdate();
        }
    }
}

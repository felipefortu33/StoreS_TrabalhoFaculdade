package main;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import BancoDeDados.DataAccessException;
import Login.LoginController;
import Login.Usuario;
import produtos.Produto;
import produtos.ProdutoDAO;
import servicos.EstoqueService;
import vendas.Venda;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProdutoDAO produtoDAO = new ProdutoDAO();
        EstoqueService estoqueService = new EstoqueService(produtoDAO);
        LoginController loginController = new LoginController();

        // Sistema de Login
        System.out.println("Bem-vindo ao sistema StoreS!");
        System.out.print("Digite seu nome de usuário: ");
        String usuario = scanner.nextLine();
        System.out.print("Digite sua senha: ");
        String senha = scanner.nextLine();

        Usuario usuarioAutenticado;
        try {
            usuarioAutenticado = loginController.autenticar(usuario, senha);
        } catch (DataAccessException exception) {
            System.out.println("Erro ao acessar o banco de dados: " + exception.getMessage());
            scanner.close();
            return;
        }

        if (usuarioAutenticado == null) {
            System.out.println("Falha no login. Encerrando o programa.");
            scanner.close();
            return;
        }

        System.out.println("Login realizado como " + usuarioAutenticado.getNivelAcesso() + ".");

        // Menu de opções
        int opcao = 0;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Cadastrar Produto Manualmente");
            System.out.println("2. Listar Produtos");
            System.out.println("3. Editar Produto");
            System.out.println("4. Remover Produto");
            System.out.println("5. Comprar de Fornecedor");
            System.out.println("6. Realizar Venda");
            System.out.println("0. Sair");
            opcao = lerInteiro(scanner, "Escolha uma opção: ", 0);

            try {
                switch (opcao) {
                case 1:
                    // Cadastro de Produto Manualmente
                    if (!usuarioAutenticado.isAdmin()) {
                        System.out.println("Acesso permitido somente para administradores.");
                        break;
                    }
                    String nomeProduto = lerTexto(scanner, "Nome do produto: ");
                    BigDecimal preco = lerDecimal(scanner, "Preço do produto: ", BigDecimal.valueOf(0.01));
                    int quantidade = lerInteiro(scanner, "Quantidade do produto: ", 0);

                    Produto produto = new Produto(nomeProduto, preco, quantidade);
                    System.out.println(produtoDAO.cadastrarProduto(produto)
                        ? "Produto cadastrado com sucesso!"
                        : "Não foi possível cadastrar o produto.");
                    break;

                case 2:
                    // Listagem de Produtos
                    System.out.println("Lista de Produtos:");
                    List<Produto> produtos = produtoDAO.listarProdutos();

                    if (produtos.isEmpty()) {
                        System.out.println("Nenhum produto cadastrado.");
                    } else {
                        for (Produto p : produtos) {
                            System.out.println("ID: " + p.getId() +
                                               ", Nome: " + p.getNome() +
                                               ", Preço: " + p.getPreco() +
                                               ", Quantidade: " + p.getQuantidade());
                        }
                    }
                    break;

                case 3:
                    // Edição de Produto
                    if (!usuarioAutenticado.isAdmin()) {
                        System.out.println("Acesso permitido somente para administradores.");
                        break;
                    }
                    int idEditar = lerInteiro(scanner, "Digite o ID do produto a ser editado: ", 1);
                    String novoNome = lerTexto(scanner, "Novo nome do produto: ");
                    BigDecimal novoPreco = lerDecimal(scanner, "Novo preço do produto: ", BigDecimal.valueOf(0.01));
                    int novaQuantidade = lerInteiro(scanner, "Nova quantidade do produto: ", 0);

                    Produto produtoEditado = new Produto(novoNome, novoPreco, novaQuantidade);
                    produtoEditado.setId(idEditar);
                    System.out.println(produtoDAO.editarProduto(produtoEditado)
                        ? "Produto atualizado com sucesso!"
                        : "Produto não encontrado ou não foi possível atualizá-lo.");
                    break;

                case 4:
                    // Remoção de Produto
                    if (!usuarioAutenticado.isAdmin()) {
                        System.out.println("Acesso permitido somente para administradores.");
                        break;
                    }
                    int idRemover = lerInteiro(scanner, "Digite o ID do produto a ser removido: ", 1);
                    System.out.println(produtoDAO.removerProduto(idRemover)
                        ? "Produto removido com sucesso!"
                        : "Produto não encontrado ou não foi possível removê-lo.");
                    break;

                case 5:
                    // Compra de Produtos
                    String nomeProdutoCompra = lerTexto(scanner, "Nome do produto para compra: ");
                    Produto produtoParaCompra = produtoDAO.buscarProdutoPorNome(nomeProdutoCompra);

                    if (produtoParaCompra == null) {
                        System.out.println("Produto não encontrado.");
                        break;
                    }

                    int quantidadeCompra = lerInteiro(scanner, "Quantidade a ser comprada: ", 1);
                    BigDecimal porcentagemLucro = lerDecimal(scanner,
                        "Porcentagem de lucro: ", BigDecimal.ZERO);

                    boolean compraRegistrada = estoqueService.registrarCompra(
                        produtoParaCompra, quantidadeCompra, porcentagemLucro);
                    System.out.println(compraRegistrada
                        ? "Compra registrada com sucesso!"
                        : "Não foi possível registrar a compra.");
                    break;

                case 6:
                    // Venda de Produtos
                    String nomeProdutoVenda = lerTexto(scanner, "Nome do produto a ser vendido: ");
                    Produto produtoParaVenda = produtoDAO.buscarProdutoPorNome(nomeProdutoVenda);

                    if (produtoParaVenda == null) {
                        System.out.println("Produto não encontrado.");
                        break;
                    }

                    int quantidadeVenda = lerInteiro(scanner, "Quantidade a ser vendida: ", 1);

                    Venda venda = estoqueService.registrarVenda(produtoParaVenda, quantidadeVenda);
                    if (venda == null) {
                        System.out.println("Não foi possível realizar a venda. "
                            + "Verifique o estoque e tente novamente.");
                        break;
                    }

                    System.out.println("Venda registrada com sucesso!");
                    System.out.println("Produto: " + venda.getProduto().getNome());
                    System.out.println("Quantidade Vendida: " + venda.getQuantidadeVendida());
                    System.out.println("Valor Total: " + venda.getValorTotal());
                    break;

                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (DataAccessException exception) {
                System.out.println("Erro ao acessar o banco de dados: " + exception.getMessage());
            }

        } while (opcao != 0);

        scanner.close();
    }

    private static String lerTexto(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String valor = scanner.nextLine().trim();
            if (!valor.isEmpty()) {
                return valor;
            }
            System.out.println("O valor não pode ficar vazio.");
        }
    }

    private static int lerInteiro(Scanner scanner, String mensagem, int minimo) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            try {
                int valor = Integer.parseInt(entrada);
                if (valor >= minimo) {
                    return valor;
                }
            } catch (NumberFormatException ignored) {
                // Solicita novamente uma entrada numerica valida.
            }
            System.out.println("Digite um numero inteiro maior ou igual a " + minimo + ".");
        }
    }

    private static BigDecimal lerDecimal(Scanner scanner, String mensagem, BigDecimal minimo) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim().replace(',', '.');
            try {
                BigDecimal valor = new BigDecimal(entrada);
                if (valor.compareTo(minimo) >= 0) {
                    return valor;
                }
            } catch (NumberFormatException ignored) {
                // Solicita novamente uma entrada numerica valida.
            }
            System.out.println("Digite um numero maior ou igual a " + minimo + ".");
        }
    }
}

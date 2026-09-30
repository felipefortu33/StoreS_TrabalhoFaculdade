package servicos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import produtos.Produto;
import produtos.ProdutoDAO;
import vendas.Venda;

class EstoqueServiceTest {
    @Test
    void compraCalculaPrecoComMargemEAtualizaEstoque() {
        FakeProdutoDAO produtoDAO = new FakeProdutoDAO();
        EstoqueService service = new EstoqueService(produtoDAO);
        Produto produto = new Produto("Produto", new BigDecimal("100.00"), 5);

        assertTrue(service.registrarCompra(produto, 3, new BigDecimal("10")));
        assertEquals(new BigDecimal("110.00"), produtoDAO.novoPreco);
        assertEquals(3, produtoDAO.quantidade);
    }

    @Test
    void vendaCalculaTotalEAtualizaEstoque() {
        FakeProdutoDAO produtoDAO = new FakeProdutoDAO();
        EstoqueService service = new EstoqueService(produtoDAO);
        Produto produto = new Produto("Produto", new BigDecimal("12.50"), 5);

        Venda venda = service.registrarVenda(produto, 2);

        assertEquals(new BigDecimal("25.00"), venda.getValorTotal());
        assertEquals(2, produtoDAO.quantidade);
        assertEquals(new BigDecimal("12.50"), produtoDAO.precoUnitario);
    }

    @Test
    void vendaAcimaDoEstoqueDeveSerRecusada() {
        FakeProdutoDAO produtoDAO = new FakeProdutoDAO();
        EstoqueService service = new EstoqueService(produtoDAO);
        Produto produto = new Produto("Produto", new BigDecimal("12.50"), 1);

        assertNull(service.registrarVenda(produto, 2));
        assertFalse(produtoDAO.vendaAtualizada);
    }

    private static class FakeProdutoDAO extends ProdutoDAO {
        private BigDecimal novoPreco;
        private BigDecimal precoUnitario;
        private int quantidade;
        private boolean vendaAtualizada;

        @Override
        public boolean atualizarEstoqueAposCompra(int id, BigDecimal novoPreco, int quantidade) {
            this.novoPreco = novoPreco;
            this.quantidade = quantidade;
            return true;
        }

        @Override
        public boolean atualizarEstoqueAposVenda(int id, int quantidade, BigDecimal precoUnitario) {
            this.quantidade = quantidade;
            this.precoUnitario = precoUnitario;
            this.vendaAtualizada = true;
            return true;
        }
    }
}

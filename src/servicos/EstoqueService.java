package servicos;

import java.math.BigDecimal;
import java.math.RoundingMode;

import produtos.Produto;
import produtos.ProdutoDAO;
import vendas.Venda;

public class EstoqueService {
    private final ProdutoDAO produtoDAO;

    public EstoqueService(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    public boolean registrarCompra(Produto produto, int quantidade, BigDecimal porcentagemLucro) {
        BigDecimal fatorLucro = BigDecimal.ONE.add(
            porcentagemLucro.divide(BigDecimal.valueOf(100), 8, RoundingMode.HALF_UP));
        BigDecimal precoVenda = produto.getPreco()
            .multiply(fatorLucro).setScale(2, RoundingMode.HALF_UP);

        return produtoDAO.atualizarEstoqueAposCompra(
            produto.getId(), precoVenda, quantidade);
    }

    public Venda registrarVenda(Produto produto, int quantidade) {
        if (quantidade > produto.getQuantidade()) {
            return null;
        }

        BigDecimal valorTotal = produto.getPreco()
            .multiply(BigDecimal.valueOf(quantidade));
        boolean estoqueAtualizado = produtoDAO.atualizarEstoqueAposVenda(
            produto.getId(), quantidade, produto.getPreco());

        if (!estoqueAtualizado) {
            return null;
        }

        return new Venda(produto, quantidade, valorTotal);
    }
}

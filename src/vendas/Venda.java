package vendas;

import java.math.BigDecimal;

import produtos.Produto;

public class Venda {
    private Produto produto;
    private int quantidadeVendida;
    private BigDecimal valorTotal;

    // Construtor
    public Venda(Produto produto, int quantidadeVendida, BigDecimal valorTotal) {
        this.produto = produto;
        this.quantidadeVendida = quantidadeVendida;
        this.valorTotal = valorTotal;
    }

    // Getters
    public Produto getProduto() {
        return produto;
    }

    public int getQuantidadeVendida() {
        return quantidadeVendida;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }
}

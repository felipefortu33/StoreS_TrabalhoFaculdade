package compras;

import java.math.BigDecimal;
import java.math.RoundingMode;

import produtos.Produto;

public class Compra {

    private Produto produto;
    private int quantidade;
    private BigDecimal margemGanho; // Percentual de ganho aplicado ao preço do produto

    public Compra(Produto produto, int quantidade, BigDecimal margemGanho) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.margemGanho = margemGanho;
    }

    // Método para calcular o preço do produto com a margem de ganho aplicada
    public BigDecimal calcularPrecoComGanho() {
        BigDecimal fatorGanho = BigDecimal.ONE.add(
            margemGanho.divide(BigDecimal.valueOf(100), 8, RoundingMode.HALF_UP));
        return produto.getPreco().multiply(fatorGanho).setScale(2, RoundingMode.HALF_UP);
    }

    // Método para calcular o valor total da compra
    public BigDecimal calcularTotal() {
        return calcularPrecoComGanho().multiply(BigDecimal.valueOf(quantidade));
    }

    // Getters e Setters
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getMargemGanho() {
        return margemGanho;
    }

    public void setMargemGanho(BigDecimal margemGanho) {
        this.margemGanho = margemGanho;
    }

    @Override
    public String toString() {
        return "Compra: \n" +
               "Produto: " + produto.getNome() + "\n" +
               "Preço unitário (com ganho): " + calcularPrecoComGanho() + "\n" +
               "Quantidade: " + quantidade + "\n" +
               "Total: " + calcularTotal();
    }
}

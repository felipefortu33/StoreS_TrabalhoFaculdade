package produtos;

import java.math.BigDecimal;

public class Produto {
    private int id;
    private String codigo;
    private String nome;
    private BigDecimal preco;
    private int quantidade;
    private String categoria;
    private int estoqueMinimo;

    public Produto(String nome, BigDecimal preco, int quantidade) {
        this(null, nome, preco, quantidade, "GERAL", 0);
    }

    public Produto(String codigo, String nome, BigDecimal preco, int quantidade,
                   String categoria, int estoqueMinimo) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.categoria = categoria;
        this.estoqueMinimo = estoqueMinimo;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getEstoqueMinimo() {
        return estoqueMinimo;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setEstoqueMinimo(int estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }
}


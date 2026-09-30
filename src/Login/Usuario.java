package Login;

public class Usuario {
    private final String nome;
    private final String nivelAcesso;

    public Usuario(String nome, String nivelAcesso) {
        this.nome = nome;
        this.nivelAcesso = nivelAcesso;
    }

    public String getNome() {
        return nome;
    }

    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public boolean isAdmin() {
        return "ADMIN".equals(nivelAcesso);
    }
}

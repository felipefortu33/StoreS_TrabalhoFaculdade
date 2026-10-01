package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import BancoDeDados.DBConnection;
import Login.PasswordHasher;

public class SetupAdmin {
    public static void main(String[] args) {
        String nomeUsuario = System.getenv("STORES_ADMIN_USER");
        String senha = System.getenv("STORES_ADMIN_PASSWORD");

        if (nomeUsuario == null || nomeUsuario.isBlank()
                || senha == null || senha.isBlank()) {
            System.err.println("Configure STORES_ADMIN_USER e STORES_ADMIN_PASSWORD.");
            return;
        }

        String sql = "INSERT INTO usuarios (nome_usuario, senha, nivel_acesso) "
                   + "VALUES (?, ?, 'ADMIN')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeUsuario.trim());
            stmt.setString(2, PasswordHasher.hash(senha));
            stmt.executeUpdate();
            System.out.println("Administrador criado com sucesso.");
        } catch (SQLException exception) {
            System.err.println("Nao foi possivel criar o administrador: "
                + exception.getMessage());
        }
    }
}

package Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import BancoDeDados.DBConnection;
import BancoDeDados.DataAccessException;

public class LoginController {
    public Usuario autenticar(String nomeUsuario, String senha) {
        String sql = "SELECT senha, nivel_acesso FROM usuarios WHERE nome_usuario = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nomeUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    if (PasswordHasher.matches(senha, rs.getString("senha"))) {
                        return new Usuario(nomeUsuario, rs.getString("nivel_acesso"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Nao foi possivel autenticar o usuario.", e);
        }
        return null;
    }
}

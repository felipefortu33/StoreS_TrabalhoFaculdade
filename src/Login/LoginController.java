package Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import BancoDeDados.DBConnection;

public class LoginController {
    public boolean autenticar(String nomeUsuario, String senha) {
        String sql = "SELECT senha FROM usuarios WHERE nome_usuario = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nomeUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return PasswordHasher.matches(senha, rs.getString("senha"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

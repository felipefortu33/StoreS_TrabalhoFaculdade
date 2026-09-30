package BancoDeDados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/stores";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "root";
    
    public static Connection getConnection() throws SQLException {
        String url = getSetting("STORES_DB_URL", "stores.db.url", DEFAULT_URL);
        String user = getSetting("STORES_DB_USER", "stores.db.user", DEFAULT_USER);
        String password = getSetting("STORES_DB_PASSWORD", "stores.db.password", DEFAULT_PASSWORD);
        return DriverManager.getConnection(url, user, password);
    }

    private static String getSetting(String environmentName, String propertyName, String defaultValue) {
        String propertyValue = System.getProperty(propertyName);
        if (propertyValue != null && !propertyValue.isBlank()) {
            return propertyValue;
        }

        String environmentValue = System.getenv(environmentName);
        if (environmentValue != null && !environmentValue.isBlank()) {
            return environmentValue;
        }

        return defaultValue;
    }
}
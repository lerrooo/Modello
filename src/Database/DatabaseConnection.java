package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection{
    private static DatabaseConnection instance;
    public static Connection connection = null;
    private String nome = "postgres";
    private String password = "admin";
    private String url = "jdbc:postgresql://localhost:5432/ProgettoPOO";
    private String driver = "org.postgresql.Driver";

    public DatabaseConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, nome, password);
        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DatabaseConnection getInstance() throws SQLException {
        // se la connessione non esiste o è chiusa ne creo una nuova
        if (instance == null) { instance = new DatabaseConnection();
        } else if (connection.isClosed()) {
            // altrimenti restituisco il riferimento a quella esistente
            instance = new DatabaseConnection();
        } return instance;
    }

}
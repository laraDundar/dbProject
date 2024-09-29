package pizzaSoftware;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnector {
    static final String DB_URL = "jdbc:mysql://localhost/PizzaOrderingSystem";
    static final String USER = "root";
    static final String PASS = "lara2004";

    public Connection connect() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(USER, PASS, DB_URL);
            System.out.println("Connected to database successfully.");
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Connection failed: " + e.getMessage());
            
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Connection closed successfully.");

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return connection;
    }
}

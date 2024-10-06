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
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to database successfully.");
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Connection failed: " + e.getMessage());
            
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("JDBC Driver cannot be found.");
        }
        return connection;
    }
}

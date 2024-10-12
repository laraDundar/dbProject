package pizzaSoftware;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class dbConnector {
    static final String DB_URL = "jdbc:mysql://localhost/PizzaOrderingSystem";
    static final String USER = "root";
    // static final String PASS = "10032005";
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

    public List<DeliveryPerson> fetchDeliveryPeople() {
        List<DeliveryPerson> deliveryPeople = new ArrayList<>();
        String query = "SELECT * FROM delivery_people"; // Adjust this query based on your table schema

        try (Connection connection = connect(); // Use your connection method
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                DeliveryPerson person = new DeliveryPerson();
                person.setDeliveryPersonId(resultSet.getInt("delivery_person_id")); // Make sure the column names match your table
                person.setName(resultSet.getString("name"));
                person.setAvailabilityStatus(resultSet.getBoolean("availability_status"));
                person.setAreaId(resultSet.getInt("area_id"));
                // Load other properties if needed
                deliveryPeople.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error fetching delivery people: " + e.getMessage());
        }

        return deliveryPeople;
    }
}

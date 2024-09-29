package pizzaSoftware;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    public static void main(String[] args) {
        dbConnector connector = new dbConnector();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connector.connect();

            if (connection != null) {
                statement = connection.createStatement();

            menu pizzaMenu = new menu(connection);
            pizzaMenu.displayMenu();
                
                /*String sqlQuery = "SELECT * FROM Customers";
                resultSet = statement.executeQuery(sqlQuery);


                while (resultSet.next()) {
                    int customerId = resultSet.getInt("customer_id");
                    String name = resultSet.getString("name");
                    String gender = resultSet.getString("gender");
                    String birthdate = resultSet.getString("birthdate");
                    String phoneNumber = resultSet.getString("phone_number");
                    String address = resultSet.getString("address");
                    String email = resultSet.getString("email");
                    String username = resultSet.getString("username");
                    String passwordHash = resultSet.getString("password_hash");
                    String zipCode = resultSet.getString("zip_code");

                    System.out.println("Customer ID: " + customerId +
                                       ", Name: " + name +
                                       ", Gender: " + gender +
                                       ", Birthdate: " + birthdate +
                                       ", Phone Number: " + phoneNumber +
                                       ", Address: " + address +
                                       ", Email: " + email +
                                       ", Username: " + username +
                                       ", Password Hash: " + passwordHash +
                                       ", Zip Code: " + zipCode);
                } */
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}

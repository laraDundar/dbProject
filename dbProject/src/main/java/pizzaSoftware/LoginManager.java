package pizzaSoftware;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginManager {
    private static final String PEPPER = "SuperSecretPepper"; // Static pepper used in hashing
    private dbConnector dbConnection;

    public LoginManager() {
        dbConnection = new dbConnector();
    }

    // Method to register a new customer
    public void registerCustomer(Customer customer, String password) {
        String salt = generateSalt();
        String passwordHash = hashPassword(password, salt, PEPPER);

        try (Connection connection = dbConnection.connect()) {
            String query = "INSERT INTO Customers (username, password_hash, salt, name, address, zip_code, phone_number, email, gender, birthdate) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, customer.getUsername());
            statement.setString(2, passwordHash);
            statement.setString(3, salt);
            statement.setString(4, customer.getName());
            statement.setString(5, customer.getAddress());
            statement.setString(6, customer.getZipCode());
            statement.setString(7, customer.getPhoneNumber());
            statement.setString(8, customer.getEmail());
            statement.setString(9, customer.getGender());
            statement.setObject(10, customer.getBirthdate());

            statement.executeUpdate();

            customer.setPasswordHash(passwordHash); // Set the hashed password in the customer object
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to log in a customer
    public boolean login(String username, String password) {
        try (Connection connection = dbConnection.connect()) {
            String query = "SELECT password_hash, salt FROM Customers WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedHash = resultSet.getString("password_hash");
                String storedSalt = resultSet.getString("salt");
                String inputHash = hashPassword(password, storedSalt, PEPPER);

                return storedHash.equals(inputHash); // Return true if hashes match
            } else {
                return false; // User doesn't exist
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Hash the password with salt and pepper
    public static String hashPassword(String password, String salt, String pepper) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String saltedPepperedPassword = salt + password + pepper;
            byte[] hashedBytes = md.digest(saltedPepperedPassword.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    // Generate a random salt
    private static String generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        StringBuilder sb = new StringBuilder();
        for (byte b : salt) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public Customer getCustomerByUsername(String username) {
        Customer customer = null;
        dbConnector connector = new dbConnector(); // Create a dbConnector instance
    
        try (Connection connection = dbConnection.connect()) {
            String query = "SELECT * FROM Customers WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
    
            if (resultSet.next()) {
                customer = new Customer();
                customer.setUsername(resultSet.getString("username"));
                customer.setName(resultSet.getString("name"));
                customer.setAddress(resultSet.getString("address"));
                
                // Pass the zip code and dbConnector instance to setZipCode
                customer.setZipCode(resultSet.getString("zip_code"), connector);
    
                customer.setPhoneNumber(resultSet.getString("phone_number"));
                customer.setEmail(resultSet.getString("email"));
                customer.setGender(resultSet.getString("gender"), connector);
                customer.setBirthdate(resultSet.getDate("birthdate").toLocalDate());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return customer;
    }
}

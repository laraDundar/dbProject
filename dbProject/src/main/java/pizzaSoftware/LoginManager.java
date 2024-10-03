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
            String query = "INSERT INTO Customers (username, password_hash, salt) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, customer.getUsername());
            statement.setString(2, passwordHash);
            statement.setString(3, salt);
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
}

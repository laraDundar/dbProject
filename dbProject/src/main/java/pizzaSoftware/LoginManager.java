package pizzaSoftware;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class LoginManager {
    private static final String PEPPER = "SuperSecretPepper"; // Static pepper used in hashing
    private Map<String, String> saltStorage = new HashMap<>(); // Store salts for each username
    private Map<String, String> passwordStorage = new HashMap<>(); // Store hashed passwords
    

    // Method to register a new customer
    public void registerCustomer(Customer customer, String password) {
        String salt = generateSalt();
        saltStorage.put(customer.getUsername(), salt); // Store the salt for this user
        String passwordHash = hashPassword(password, salt, PEPPER);
        passwordStorage.put(customer.getUsername(), passwordHash); // Store the hashed password
        customer.setPasswordHash(passwordHash); // Set the hashed password in the customer object
    }

    // Method to log in a customer
    public boolean login(String username, String password) {
        if (!passwordStorage.containsKey(username)) {
            return false; // User doesn't exist
        }
        String storedSalt = saltStorage.get(username);
        String storedHash = passwordStorage.get(username);
        String inputHash = hashPassword(password, storedSalt, PEPPER);
        return storedHash.equals(inputHash); // Return true if hashes match
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

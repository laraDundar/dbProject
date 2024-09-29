import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        LoginManager loginManager = new LoginManager();
        
        // Create a new customer
        Customer customer = new Customer("tomtom@gmail.com", "tomtom", "Tom", "Depp", "Male", LocalDate.of(1995,05,12), "123456789", "spring Day 2567TH");
        
        // Register the customer with a password
        loginManager.registerCustomer(customer, "SecurePassword123");
        
        // Try logging in with correct credentials
        boolean success = loginManager.login("tomtom", "SecurePassword123");
        System.out.println("Login successful: " + success); // Should print true
        
        // Try logging in with incorrect password
        boolean failure = loginManager.login("tomtom", "WrongPassword");
        System.out.println("Login successful: " + failure); // Should print false

        boolean failUserName = loginManager.login("littlepig", "WrongPassword");
        System.out.println("Login successful: " + failUserName); // Should print false
    }
}

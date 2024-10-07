package pizzaSoftware;

public class SessionManager {
    private static SessionManager instance;
    private static Customer loggedInCustomer;

    private SessionManager() {
        // Private constructor to prevent instantiation
    }

    // Get the single instance of the SessionManager
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    // Store the logged-in customer
    public static void setLoggedInCustomer(Customer customer) {
        loggedInCustomer = customer;
    }

    // Get the logged-in customer
    public static Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }
}

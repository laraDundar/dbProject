package pizzaSoftware;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*LoginManager loginManager = new LoginManager();
        
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
        System.out.println("Login successful: " + failUserName); // Should print false*/

        // Create a menu service instance (dummy implementation for testing)
       // Create a MenuService instance
        // Create a customer
        // Create a MenuService instance

        // Create a sample customer
        /*Customer customer = new Customer("tomtom@gmail.com", "tomtom", "Tom", "Depp", "Male", LocalDate.of(1995,05,12), "123456789", "spring Day 2567TH");

        // Create a sample OrderService
        OrderService orderService = new OrderService();

        // Sample pizza, drink, and dessert IDs (assuming these IDs exist in your database)
        List<Integer> pizzaIds = Arrays.asList(1, 1, 2); // Two of pizza ID 1, and one of pizza ID 2
        List<Integer> drinkIds = Arrays.asList(3);
        List<Integer> dessertIds = Arrays.asList(4);

        // Place an order (no actual saving to database)
        orderService.placeOrder(customer, pizzaIds, drinkIds, dessertIds, "DISCOUNT10");*/

        // Create a new instance of the database connector

   /*  OrderService orderService = new OrderService(); // Create an instance of OrderService

    // Assuming you have a Customer class and can create a new customer
    Customer customer = new Customer("tomtom@gmail.com", "tomtom", "Tom", "Depp", "Male", LocalDate.of(1995,05,12), "123456789", "spring Day 2567TH", "567TH");
 // Create a customer (use appropriate constructor)

    // List of pizza IDs to order
    //List<Integer> pizzaIds = Arrays.asList(1, 2); // Replace with actual pizza IDs
    List<Integer> pizzaIds = Arrays.asList(1, 1, 2); // Two of pizza ID 1, and one of pizza ID 2
        List<Integer> drinkIds = Arrays.asList(3);
        List<Integer> dessertIds = Arrays.asList(4);

    // Place the order using the placeOrder method
    Order orderPlaced = orderService.placeOrder(customer, pizzaIds, drinkIds, dessertIds, "DISCOUNT10");


    // Check if the order was placed successfully
    if (orderPlaced != null) {
        System.out.println("Order placed successfully!");
    } else {
        System.out.println("Failed to place the order.");
    }*/

    /*int pizzaId = 1; // ID of the pizza you want to test

        // Test the method to get ingredients for a pizza by pizzaId
        List<PizzaIngredient> ingredients = PizzaIngredient.getIngredientsByPizzaId(pizzaId);
        
        if (ingredients.isEmpty()) {
            System.out.println("No ingredients found for pizza with ID " + pizzaId);
        } else {
            System.out.println("Ingredients for pizza with ID " + pizzaId + ":");
            for (PizzaIngredient ingredient : ingredients) {
                Ingredient ing = ingredient.getIngredient();
                System.out.println("- " + ing.getIngredientName() + " | Quantity: " + ingredient.getQuantity() +
                                   " | Price per unit: " + ingredient.getPriceIngredientPerQuantity() +
                                   " | Vegetarian: " + ing.isVegetarian() +
                                   " | Vegan: " + ing.isVegan());
            }
        }*/

        LoginManager loginManager = new LoginManager();

        // Step 1: Register a new customer
        Customer customer = new Customer(null, null, null, null, null, null, null, null, null);
        customer.setUsername("john_doe");
        customer.setPizzaOrderCount(11);
        customer.setBirthdate(LocalDate.of(2005, 10, 06));
        
        String password = "password123";
        /* 
        System.out.println("Registering customer...");
        loginManager.registerCustomer(customer, password);

        // Step 2: Test correct login
        System.out.println("Logging in with correct password...");
        boolean loginSuccess = loginManager.login("john_doe", "password123");
        System.out.println("Login successful: " + loginSuccess); // Expected output: true

        // Step 3: Test incorrect login
        System.out.println("Logging in with incorrect password...");
        boolean loginFail = loginManager.login("john_doe", "wrongpassword");
        System.out.println("Login successful with wrong password: " + loginFail); // Expected output: false*/

        // Sample customer registration and login
      
        
       
            
            // Create a list of pizza IDs to order
            List<Integer> pizzaIds = new ArrayList<>();
            pizzaIds.add(1); // Assuming pizza ID 1 exists
            pizzaIds.add(2); // Assuming pizza ID 2 exists
            
            // Create a list of drink IDs to order
            List<Integer> drinkIds = new ArrayList<>();
            drinkIds.add(1); // Assuming drink ID 1 exists
            
            // No desserts in this order
            List<Integer> dessertIds = new ArrayList<>();
            
            // Create an order
            OrderService orderService = new OrderService();
            
            Order order = orderService.placeOrder(customer, pizzaIds, drinkIds, dessertIds);
            
            // Print out order details
            /*System.out.println("Order placed successfully! yey");
            System.out.println("Total Price yey: " + order.getPrice());
            System.out.println("Estimated Preparation Time yey: " + order.getEstimatedDeliveryTime() + " minutes");*/

            // Attempt to cancel the order
        //int orderIdToCancel = order.getOrderId(); // Assuming Order class has a method to get order ID
       // boolean isCancelled = orderService.cancelOrder(orderIdToCancel);

        // Print cancellation result
        /*if (isCancelled) {
            System.out.println("Order cancelled successfully.");
        } else {
            System.out.println("Failed to cancel the order or cancellation period has expired.");
        }*/

         // For testing: Simulate a specific order ID directly (for ID 26)
         /*int specificOrderIdToCancel = 26; // The specific order ID you want to cancel
         boolean specificIsCancelled = orderService.cancelOrder(specificOrderIdToCancel);
 
         if (specificIsCancelled) {
             System.out.println("Specific order with ID 26 cancelled successfully.");
         } else {
             System.out.println("Failed to cancel the specific order with ID 26.");
         }*/
       
    
    }
}

        
    


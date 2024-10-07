package pizzaSoftware;

import java.security.Timestamp;
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

        //Step 1: Register a new customer
        Customer customer = new Customer("ghgh@hjghj", "test new", "John", "male", LocalDate.of(1997, 10, 06), "564646", "Nido ghgh", "1000AB");
        //customer.setUsername("john_doe");
        //customer.setPizzaOrderCount(11);
        //customer.setBirthdate(LocalDate.of(2005, 10, 06));
        
        
        String password = "password123";
        
        System.out.println("Registering customer...");
        loginManager.registerCustomer(customer, password);

        // Step 2: Test correct login
        System.out.println("Logging in with correct password...");
        boolean loginSuccess = loginManager.login("test new", "password123");
        System.out.println("Login successful: " + loginSuccess); // Expected output: true

        // Step 3: Test incorrect login
        System.out.println("Logging in with incorrect password...");
        boolean loginFail = loginManager.login("john_doe", "wrongpassword");
        System.out.println("Login successful with wrong password: " + loginFail); // Expected output: false
        dbConnector dbConnector = new dbConnector();
        //customer.retrieveCustomerDetails(dbConnector);
        customer.retrieveCustomerId(dbConnector);
        System.out.println("!!!test!customer id " + customer.getCustomerId());
        System.out.println(customer.getAddress());
        System.out.println(customer.getZipCode());

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
            System.out.println("Order placed successfully! yey");
            System.out.println("Total Price yey: " + order.getPrice());
            System.out.println("Estimated Preparation Time yey: " + order.getEstimatedPreparationTime() + " minutes");

            //Attempt to cancel the order
        int orderIdToCancel = order.getOrderId(); // Assuming Order class has a method to get order ID
       boolean isCancelled = orderService.cancelOrder(orderIdToCancel);

        // Print cancellation result
        if (isCancelled) {
            System.out.println("Order cancelled successfully.");
        } else {
            System.out.println("Failed to cancel the order or cancellation period has expired.");
        }

         // For testing: Simulate a specific order ID directly (for ID 26)
         /*int specificOrderIdToCancel = 26; // The specific order ID you want to cancel
         boolean specificIsCancelled = orderService.cancelOrder(specificOrderIdToCancel);
 
         if (specificIsCancelled) {
             System.out.println("Specific order with ID 26 cancelled successfully.");
         } else {
             System.out.println("Failed to cancel the specific order with ID 26.");
         }*/
       
    

        // Create the necessary objects for testing
        //MenuService menuService = new MenuService();
       /*  OrderService orderService = new OrderService();

        // Create a sample customer (you can adjust the details)
        //Customer customer = new Customer("john@doe.com", "mytest", "John", "Doe", "M", LocalDate.of(1990, 1, 1),
        //                                 "0612345678", "123 Main St", "1000AB");

        // Create some pizza, drink, and dessert IDs for the order
        List<Integer> pizzaIds = new ArrayList<>();
        pizzaIds.add(1); // Add the pizza IDs that exist in your menu

        List<Integer> drinkIds = new ArrayList<>();
        drinkIds.add(1); // Add the drink IDs that exist in your menu

        List<Integer> dessertIds = new ArrayList<>();
        dessertIds.add(1); // Add the dessert IDs that exist in your menu

       // Place the order using the OrderService
       Order order = orderService.placeOrder(customer, pizzaIds, drinkIds, dessertIds);

       // Output the estimated preparation time and delivery time
       int estimatedPrepTime = orderService.estimatePreparationTime(pizzaIds.size());
       System.out.println("Estimated Preparation Time: " + estimatedPrepTime + " minutes");

       // Simulate the delivery service
       DeliveryService deliveryService = new DeliveryService(customer.getZipCode());

       // Test Case 1: Delivery Person is Available
       System.out.println("\n*** Test Case 1: Delivery Person is Available ***");

       // Setup an available delivery person
       DeliveryPerson deliveryPersonAvailable = new DeliveryPerson (6, "Max", 1); // ID 1, covers zip code "1000AB"
       deliveryService.addDeliveryPerson(deliveryPersonAvailable); // Add this delivery person to the system

       // Manually setting up delivery areas and distance
       DeliveryArea deliveryArea = new DeliveryArea(1, customer.getZipCode(), 2); // Distance = 2 (far)
       
       // Estimate delivery time for the order based on distance
       int estimatedDeliveryTime = deliveryService.estimateDeliveryTime(deliveryArea.getDistance());
       System.out.println("Estimated Delivery Time: " + estimatedDeliveryTime + " minutes");

       // Combine preparation and delivery time for total estimate
       int totalEstimateTime = estimatedPrepTime + estimatedDeliveryTime;
       System.out.println("Total Estimated Time (Preparation + Delivery): " + totalEstimateTime + " minutes");

       // Assign delivery person based on zip code and availability
       deliveryService.assignDeliveryPersonToBatch(customer.getZipCode());

       // Test Case 2: No Delivery Person Available
       System.out.println("\n*** Test Case 2: No Delivery Person is Available ***");

       // Remove the available delivery person to simulate no availability
       deliveryService.clearDeliveryPeople(); // Clear all delivery people

       // Estimate delivery time again (expecting no available person)
       DeliveryBatch newBatch = deliveryService.findDeliveryBatch(order, customer.getZipCode());
       if (newBatch.getDeliveryPerson() == null) {
           System.out.println("No delivery person is available for zip code: " + customer.getZipCode());
       } else {
           System.out.println("Delivery person assigned: " + newBatch.getDeliveryPerson().getName());*/
       //}
   }
    }

        
    


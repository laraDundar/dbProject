package pizzaSoftware;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryService {
    private List<Order> orders; // List to hold orders
    private List<DeliveryPerson> deliveryPeople; // List of delivery people
    private List<DeliveryBatch> deliveryBatches; // List of delivery batches
    private String zipCode;
    private dbConnector dbConnector;

    public DeliveryService(String zipCode) {
        orders = new ArrayList<>();
        deliveryPeople = new ArrayList<>();
        deliveryBatches = new ArrayList<>();
        this.zipCode = zipCode;
        dbConnector = new dbConnector();
    }

    public void createDelivery(Order order, String deliveryAddress) {
        Delivery delivery = new Delivery(zipCode, order);
        saveDeliveryToDatabase(delivery);
    }

    private void saveDeliveryToDatabase(Delivery delivery) {
        String sql = "INSERT INTO Deliveries (order_id, start_time, status) VALUES (?, ?, ?)";
        try (Connection conn = dbConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, delivery.getOrderId());
            pstmt.setTimestamp(3, delivery.getStartTime());
            pstmt.setString(4, delivery.getStatus());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // Handle exceptions
        }
    }

    // Method to place an order
    public void placeDelivery(Order order) {
        
        //orders.add(order);
        //order.setOrderTimestamp(Timestamp.valueOf(LocalDateTime.now()));

        // Schedule delivery after 5 minutes
        createDelivery(order, zipCode);
        scheduleDelivery(order);
        DeliveryBatch deliveryBatch = findDeliveryBatch(order, zipCode);
        DeliveryPerson deliveryPerson = deliveryBatch.getDeliveryPerson();
        DeliveryArea deliveryArea = deliveryPerson.getDeliveryArea(deliveryPerson.getDeliveryPersonId());
        System.out.println("Estimated delivey time yey: " + estimateDeliveryTime(deliveryArea.getDistance()));
    }

    // Method to schedule delivery
    private void scheduleDelivery(Order order) {

        // Check if delivery can be grouped into a batch
        DeliveryBatch batch = findDeliveryBatch(order, zipCode);
        if (batch == null) {
            // Create a new batch if no suitable batch is found
            batch = new DeliveryBatch(zipCode);
            deliveryBatches.add(batch);
        }
        
        batch.addOrder(order);
        
        // Check if the batch is full (3 pizzas max)
        if (batch.getOrders().size() >= 3) {
            processDelivery(batch);
        }
    }

    // Method to find a delivery batch for grouping
private DeliveryBatch findDeliveryBatch(Order order, String zipCode) {
    // Check existing delivery batches for a matching postal code
    for (DeliveryBatch batch : deliveryBatches) {
        if (batch.getPostalCode().equals(zipCode) &&
            ChronoUnit.MINUTES.between(batch.getStartTime(), LocalDateTime.now()) <= 3) {
            return batch; // Return existing batch
        }
    }


     // If no existing batch is found, create a new one
     DeliveryBatch newBatch = new DeliveryBatch(zipCode);

     // Find an available delivery person based on the zip code
     DeliveryPerson assignedDeliveryPerson = findAvailableDeliveryPerson();
     if (assignedDeliveryPerson != null) {
         newBatch.setDeliveryPerson(assignedDeliveryPerson); // Assign the delivery person to the batch
     } else {
         System.out.println("No available delivery person found for zip code: " + zipCode);
     }
 
     deliveryBatches.add(newBatch); // Add the new batch to the collection
     return newBatch; // Return the new delivery batch
}
    
    // Method to process delivery
    private void processDelivery(DeliveryBatch batch) {
        DeliveryPerson availableDeliveryPerson = findAvailableDeliveryPerson();
        if (availableDeliveryPerson != null) {
            batch.setDeliveryPerson(availableDeliveryPerson);
            availableDeliveryPerson.setUnavailable(30); // Unavailable for 30 minutes
            // Proceed with delivery logic
            // Update batch status, etc.
        }
    }

    // Method to find an available delivery person
    private DeliveryPerson findAvailableDeliveryPerson() {
        for (DeliveryPerson person : deliveryPeople) {
            // Assuming you have a method isAvailable() in DeliveryPerson
            if (person.isAvailable() && person.getDeliveryArea(person.getDeliveryPersonId()).getZipCode().contains(zipCode)) {
                return person; // Return the first available delivery person for the zip code
            }
        }
        return null; // No available delivery person found
    }

    // Method to estimate delivery time based on distance
    public int estimateDeliveryTime(int distance) {
        int baseTime = 10; // Base time in minutes
        return baseTime + (distance * 5); // Adjust based on distance and number of pizzas
    }
}


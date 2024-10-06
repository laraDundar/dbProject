package pizzaSoftware;

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

    public DeliveryService(String zipCode) {
        orders = new ArrayList<>();
        deliveryPeople = new ArrayList<>();
        deliveryBatches = new ArrayList<>();
        this.zipCode = zipCode;
    }

    // Method to place an order
    public void placeOrder(Order order) {
        orders.add(order);
        order.setOrderTimestamp(Timestamp.valueOf(LocalDateTime.now()));

        // Schedule delivery after 5 minutes
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
        for (DeliveryBatch batch : deliveryBatches) {
            if (batch.getPostalCode().equals(zipCode) &&
                ChronoUnit.MINUTES.between(batch.getStartTime(), LocalDateTime.now()) <= 3) {
                return batch;
            }
        }
        return null;
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
            if (person.isAvailable()) {
                return person;
            }
        }
        return null;
    }

    // Method to estimate delivery time based on distance
    public int estimateDeliveryTime(int distance) {
        int baseTime = 10; // Base time in minutes
        return baseTime + (distance * 5); // Adjust based on distance and number of pizzas
    }
}


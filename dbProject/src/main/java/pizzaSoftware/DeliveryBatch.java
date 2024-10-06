package pizzaSoftware;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DeliveryBatch {
    private List<Order> orders; // List of orders in this batch
    private LocalDateTime startTime; // When the batch was created
    private DeliveryPerson deliveryPerson; // Assigned delivery person
    private String postalCode; // The postal code for this batch

    public DeliveryBatch(String postalCode) {
        this.orders = new ArrayList<>();
        this.startTime = LocalDateTime.now();
        this.postalCode = postalCode;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    // Getters and Setters
    public List<Order> getOrders() {
        return orders;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public DeliveryPerson getDeliveryPerson() {
        return deliveryPerson;
    }

    public void setDeliveryPerson(DeliveryPerson deliveryPerson) {
        this.deliveryPerson = deliveryPerson;
    }

    public String getPostalCode() {
        return postalCode;
    }
}

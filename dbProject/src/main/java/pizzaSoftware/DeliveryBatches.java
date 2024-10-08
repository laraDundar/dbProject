package pizzaSoftware;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DeliveryBatches {
    private int batchId; // Unique identifier for the batch
    private String postalCode; // Postal code for the delivery area
    private DeliveryPerson deliveryPerson; // Delivery person assigned to the batch
    private List<Order> orders; // List of orders in this batch
    private Timestamp startTime; // Start time of the delivery batch

    public DeliveryBatches(String postalCode) {
        this.postalCode = postalCode;
        this.orders = new ArrayList<>();
        this.startTime = new Timestamp(System.currentTimeMillis());
    }

    // Getters and setters
    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public DeliveryPerson getDeliveryPerson() {
        return deliveryPerson;
    }

    public void setDeliveryPerson(DeliveryPerson deliveryPerson) {
        this.deliveryPerson = deliveryPerson;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }
}

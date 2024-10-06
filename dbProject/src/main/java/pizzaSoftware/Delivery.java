package pizzaSoftware;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Delivery {
    private String zipCode;
    private Timestamp startTime;
    private int orderId; // Assuming this refers to the order being delivered
    private String status; // e.g., "Pending", "In Transit", "Delivered"
    
    public Delivery(String zipCode, Order order) {
        this.zipCode = zipCode;
        this.orderId = order.getOrderId();
        LocalDateTime currentTime = LocalDateTime.now().minusMinutes(5); // Subtract 5 minutes
        this.startTime = Timestamp.valueOf(currentTime.plusMinutes(order.getEstimatedPreparationTime())); // Add estimated preparation time
        
        this.status = "Pending"; // Initial status
    }

    // Getters and Setters
    public String getZipCode() {
        return zipCode;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
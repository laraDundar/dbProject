package pizzaSoftware;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int orderId;
    private int customerId;
    private Timestamp orderTimestamp;
    private String status;
    private int estimatedPreparationTime;
    private double price;
    private int deliveryId;
    private double priceDiscounted;
    private List<OrderItem> orderItems;//list of items in the order

    //this is ok
    //getters and setters for an order

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setEstimatedPreparationTime(int estimatedPreparationTime){
        this.estimatedPreparationTime = estimatedPreparationTime;
    }

    public int getEstimatedPreparationTime(){
        return estimatedPreparationTime;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Timestamp getOrderTimestamp() {
        return orderTimestamp;
    }

    public void setOrderTimestamp(Timestamp orderTimestamp) {
        this.orderTimestamp = orderTimestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

   

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Integer deliveryId) {
        this.deliveryId = deliveryId;
    }

    public double getPriceDiscounted() {
        return priceDiscounted;
    }

    public void setPriceDiscounted(double priceDiscounted) {
        this.priceDiscounted = priceDiscounted;
    }
}

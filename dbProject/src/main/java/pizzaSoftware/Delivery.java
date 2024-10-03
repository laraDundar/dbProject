package pizzaSoftware;

import java.sql.Timestamp;

public class Delivery {
    private int deliveryId;
    private int deliveryPersonId;
    private Timestamp startTime;
    private Timestamp endTime;
    private String status;
    private DeliveryPerson deliveryPerson;
    private DeliveryArea deliveryArea;
    private DeliveryPersonArea deliveryPersonArea;

   // Constructor for Delivery
   public Delivery(int deliveryPersonId, DeliveryPerson deliveryPersonService, DeliveryArea deliveryAreaService, DeliveryPersonArea deliveryPersonAreaService) {
    this.deliveryPersonId = deliveryPersonId;
    this.deliveryPerson = deliveryPersonService;
    this.deliveryArea = deliveryAreaService;
    this.deliveryPersonArea = deliveryPersonAreaService;
    
    // Retrieve the delivery area for the given delivery person
    //this.deliveryArea = getDeliveryAreaByDeliveryPersonId(deliveryPersonId);
}

    // Method to get the Delivery Area linked to the delivery person
    /*private int getDeliveryAreaByDeliveryPersonId(int deliveryPersonId) {
        // Retrieve the areas assigned to the delivery person
        DeliveryPersonArea deliveryPersonArea = deliveryPersonArea.getAreasByDeliveryPersonId(deliveryPersonId).get(0); // Assume only one area is linked
        return deliveryPersonArea.getAreaId();
    }
*/
    // only getters and setters for attributes of Delivery
    
    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public int getDeliveryPersonId() {
        return deliveryPersonId;
    }

    public void setDeliveryPersonId(int deliveryPersonId) {
        this.deliveryPersonId = deliveryPersonId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

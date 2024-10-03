package pizzaSoftware;

public class DeliveryPerson {
    private int deliveryPersonId;
    private String name;
    private boolean availabilityStatus;// a delivery person can be available (1) or not (0)

    //getters and setters for delivery person
    
    public int getDeliveryPersonId() {
        return deliveryPersonId;
    }

    public void setDeliveryPersonId(int deliveryPersonId) {
        this.deliveryPersonId = deliveryPersonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvailabilityStatus (boolean availabilityStatus){
        this.availabilityStatus = availabilityStatus;
    }

    public boolean getAvailabilityStatus (){
        return availabilityStatus;
    }
}

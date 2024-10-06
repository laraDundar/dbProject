package pizzaSoftware;

import java.time.LocalDateTime;

public class DeliveryPerson {
    private int deliveryPersonId;
    private String name;
    private int areaId; // Area they are assigned to
    private boolean availabilityStatus; // true if available, false otherwise
    private LocalDateTime unavailableUntil; // Track when they become available again
    private DeliveryArea deliveryArea;

    public DeliveryPerson(int deliveryPersonId, String name, int areaId) {
        this.deliveryPersonId = deliveryPersonId;
        this.name = name;
        this.areaId = areaId;
        this.availabilityStatus = true; // Initially available
    }

    public void setUnavailable(int minutes) {
        this.availabilityStatus = false;
        this.unavailableUntil = LocalDateTime.now().plusMinutes(minutes);
    }

    public boolean isAvailable() {
        // Check if the delivery person is currently available
        if (!availabilityStatus && LocalDateTime.now().isAfter(unavailableUntil)) {
            availabilityStatus = true; // They are now available
        }
        return availabilityStatus;
    }

    // Getters
    public int getDeliveryPersonId() {
        return deliveryPersonId;
    }

    public String getName() {
        return name;
    }

    public int getAreaId() {
        return areaId;
    }

    public DeliveryArea getDeliveryArea(int deliveryPersonId){
        return deliveryArea;
    }
}

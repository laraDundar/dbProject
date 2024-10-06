package pizzaSoftware;

public class DeliveryArea {
    private int areaId; // Unique identifier for the area
    private String zipCode; // Postal code for this area
    private int distance; // Distance from the restaurant (1 = close, 2 = far, 3 = very far)

    public DeliveryArea(int areaId, String zipCode, int distance) {
        this.areaId = areaId;
        this.zipCode = zipCode;
        this.distance = distance;
    }

    // Getters
    public int getAreaId() {
        return areaId;
    }


    public String getZipCode() {
        return zipCode;
    }

    public int getDistance() {
        return distance;
    }
}
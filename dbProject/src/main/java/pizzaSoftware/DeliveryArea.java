package pizzaSoftware;

public class DeliveryArea {
    private int areaId;
    private String zipCode;
    private int distance;

    /**
     * constructor for Delivery area 
     * this class just keeps track of the areas where a delivery can be done and devides the city 
     * in 3 categories based on the distance of delivery - close, far, very far = 1, 2, or 3
     * @param areaId
     * @param zipCode
     * @param distance
     */
    
    public DeliveryArea(int areaId, String zipCode, int distance){
        this.areaId = areaId;
        this.zipCode = zipCode;
        this.distance = distance;
    }

    //all getters and setters for Delivery area

    public int getDistance(){
        return distance;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}

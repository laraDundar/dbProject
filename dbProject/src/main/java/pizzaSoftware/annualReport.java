package pizzaSoftware;

public class annualReport {
    private int orderId;
    private double totalAmount;
    private String gender;
    private String age;
    private String region;

    public annualReport(int orderId, double totalAmount, String gender, String age, String region) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.gender = gender;
        this.age = age;
        this.region = region;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
    
}

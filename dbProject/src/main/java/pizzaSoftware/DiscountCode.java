package pizzaSoftware;

public class DiscountCode {
    private int discountCodeId;
    private String code;
    private double discountAmount;
    private boolean isActive;

    public int getDiscountCodeId() {
        return discountCodeId;
    }

    public void setDiscountCodeId(int discountCodeId) {
        this.discountCodeId = discountCodeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

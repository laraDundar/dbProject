package pizzaSoftware;

public class Drink {
    private int drinkId;
    private String drinkName;
    private double price;

    //this is ok
    /** 
     * constructor for Drink
     */
    public Drink(int drinkId, String name, double price) {
        this.drinkId = drinkId;
        this.drinkName = name;
        this.price = price;
    }

    //getters and setters for drink

    public int getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(int drinkId) {
        this.drinkId = drinkId;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

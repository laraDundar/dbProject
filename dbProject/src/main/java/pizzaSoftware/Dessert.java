package pizzaSoftware;

public class Dessert {
    private int dessertId;
    private String dessertName;
    private double price;

    //this is ok
    /** 
     * constructor for Dessert
     */
    public Dessert(int dessertId, String name, double price) {
        this.dessertId = dessertId;
        this.dessertName = name;
        this.price = price;
    }

    //getters and setters for Dessert
    public int getDessertId() {
        return dessertId;
    }

    public void setDessertId(int dessertId) {
        this.dessertId = dessertId;
    }

    public String getDessertName() {
        return dessertName;
    }

    public void setDessertName(String dessertName) {
        this.dessertName = dessertName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

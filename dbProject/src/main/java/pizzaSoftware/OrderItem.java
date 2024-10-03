package pizzaSoftware;

public class OrderItem {
    private int orderItemId;
    private int orderId;
    private int pizzaId;
    private int quantity;
    private int dessertId;
    private int drinkId;

    /**
     * constructor for orderItem with all the details
     * @param orderItemId
     * @param orderId
     * @param pizzaId
     * @param dessertId
     * @param drinkId
     * @param quantity
     */
    //this is ok
    public OrderItem(int orderItemId, int orderId, int pizzaId, int dessertId, int drinkId, int quantity) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.pizzaId = pizzaId;
        this.dessertId = dessertId;
        this.drinkId = drinkId;
        this.quantity = quantity;
    }

    //getters and setters for orderItem
    public OrderItem(){

    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(int pizzaId) {
        this.pizzaId = pizzaId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDessertId() {
        return dessertId;
    }

    public void setDessertId(int dessertId) {
        this.dessertId = dessertId;
    }

    public int getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(int drinkId) {
        this.drinkId = drinkId;
    }
}

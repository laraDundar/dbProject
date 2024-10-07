package pizzaSoftware;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    // DB methods
    // 1. Insert OrderItem into the DB
    /*public void insertOrderItem(Connection connection, int order_id) throws SQLException {
        String query = "INSERT INTO Order_Items (order_id, pizza_id, drink_id, dessert_id, quantity) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, order_id);
            statement.setInt(2, this.getPizzaId());
            statement.setInt(3, this.getDrinkId());
            statement.setInt(4, this.getDessertId());
            statement.setInt(5, this.getQuantity());
            statement.executeUpdate();

            // Retrieve generated orderItemId
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.orderItemId = generatedKeys.getInt(1);
                }
            }
        }
    }*/
    public void insertOrderItem(Connection connection, int order_id) throws SQLException {
    String query = "INSERT INTO Order_Items (order_id, pizza_id, drink_id, dessert_id, quantity) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
        statement.setInt(1, order_id); // Set the order ID

        // Set pizza ID (assumed to always have a value)
        statement.setInt(2, this.pizzaId); // Pizza ID

        // Check and set drink ID or NULL
        if (this.drinkId > 0) {
            statement.setInt(3, this.drinkId); // Valid drink ID
        } else {
            statement.setNull(3, java.sql.Types.INTEGER); // Set as NULL
        }

        // Check and set dessert ID or NULL
        if (this.dessertId > 0) {
            statement.setInt(4, this.dessertId); // Valid dessert ID
        } else {
            statement.setNull(4, java.sql.Types.INTEGER); // Set as NULL
        }

        statement.setInt(5, this.quantity); // Set quantity

        // Execute the insert statement
        statement.executeUpdate();

        // Retrieve generated orderItemId
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                this.orderItemId = generatedKeys.getInt(1); // Populating orderItemId
            }
        }
    }
}

    // 2. Retrieve an OrderItem by orderItemId
    public void getOrderItemById(Connection connection, int orderItemId) throws SQLException {
        String query = "SELECT * FROM Order_Items WHERE order_item_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderItemId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.orderItemId = resultSet.getInt("order_item_id");
                this.orderId = resultSet.getInt("order_id");
                this.pizzaId = resultSet.getInt("pizza_id");
                this.drinkId = resultSet.getInt("drink_id");
                this.dessertId = resultSet.getInt("dessert_id");
                this.quantity = resultSet.getInt("quantity");
            }
        }
    }

    // 3. Update OrderItem in the DB
    public void updateOrderItem(Connection connection) throws SQLException {
        String query = "UPDATE Order_Items SET order_id = ?, pizza_id = ?, drink_id = ?, dessert_id = ?, quantity = ? WHERE order_item_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, this.orderId);
            statement.setInt(2, this.pizzaId);
            statement.setInt(3, this.drinkId);
            statement.setInt(4, this.dessertId);
            statement.setInt(5, this.quantity);
            statement.setInt(6, this.orderItemId);
            statement.executeUpdate();
        }
    }
}

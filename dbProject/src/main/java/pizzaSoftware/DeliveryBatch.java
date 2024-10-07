package pizzaSoftware;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DeliveryBatch {
    private List<Order> orders; // List of orders in this batch
    private LocalDateTime startTime; // When the batch was created
    private DeliveryPerson deliveryPerson; // Assigned delivery person
    private String postalCode; // The postal code for this batch
    private int batchId; // Unique identifier for the batch

    public DeliveryBatch(String postalCode) {
        this.orders = new ArrayList<>();
        this.startTime = LocalDateTime.now();
        this.postalCode = postalCode;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    // Constructor for retrieving a DeliveryBatch from the database
    /**public DeliveryBatch(int batchId, dbConnector dbConnection) {
        this.orders = new ArrayList<>();
        retrieveFromDatabase(batchId, dbConnection);
    }*/

    // Method to save the batch and its orders to the database
    public void saveToDatabase(dbConnector dbConnection) {
        String query = "INSERT INTO delivery_batches (delivery_person_id) VALUES (?, ?, ?)";

        try (Connection connection = dbConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            //statement.setTimestamp(1, Timestamp.valueOf(startTime));
            //statement.setString(2, postalCode);
            if (deliveryPerson != null) {
                statement.setInt(3, deliveryPerson.getDeliveryPersonId());
            } else {
                statement.setNull(3, 0); // No delivery person assigned yet
            }

            statement.executeUpdate();

            // Get the generated batch ID and set it
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.batchId = generatedKeys.getInt(1);
                }
            }

            // Save the orders associated with this batch
            saveOrdersToDatabase(dbConnection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper method to save orders for this batch to the database
    private void saveOrdersToDatabase(dbConnector dbConnection) {
        String query = "INSERT INTO delivery_batches (batch_id, order_id) VALUES (?, ?)";

        try (Connection connection = dbConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            for (Order order : orders) {
                statement.setInt(1, batchId);
                statement.setInt(2, order.getOrderId());
                statement.addBatch(); // Add to batch for performance
            }

            statement.executeBatch(); // Execute all inserts at once

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     // Method to retrieve a DeliveryBatch from the database
     /*public void retrieveFromDatabase(int batchId, dbConnector dbConnection) {
        String query = "SELECT * FROM delivery_batches WHERE batch_id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, batchId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    this.batchId = batchId;
                    //this.startTime = resultSet.getTimestamp("start_time").toLocalDateTime();
                    //this.postalCode = resultSet.getString("postal_code");

                    int deliveryPersonId = resultSet.getInt("delivery_person_id");
                    if (deliveryPersonId != 0) {
                        this.deliveryPerson = new DeliveryPerson(deliveryPersonId, dbConnection);
                    }

                    retrieveOrdersFromDatabase(dbConnection);
                } else {
                    System.out.println("No delivery batch found with the given ID.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    // Helper method to retrieve the orders for this batch from the database
    /*private void retrieveOrdersFromDatabase(dbConnector dbConnection) {
        String query = "SELECT order_id FROM deliveries WHERE batch_id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, batchId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int orderId = resultSet.getInt("order_id");
                    Order order = new Order(orderId, dbConnection); // Assuming an Order constructor that retrieves from DB
                    orders.add(order);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/


    // Getters and Setters
    public List<Order> getOrders() {
        return orders;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public DeliveryPerson getDeliveryPerson() {
        return deliveryPerson;
    }

    public void setDeliveryPerson(DeliveryPerson deliveryPerson) {
        this.deliveryPerson = deliveryPerson;
    }

    public String getPostalCode() {
        return postalCode;
    }

    /*public void setDeliveryPerson(DeliveryPerson deliveryPerson, dbConnector dbConnection) {
        this.deliveryPerson = deliveryPerson;
        updateDeliveryPersonInDatabase(dbConnection); // Update the DB when delivery person is assigned
    }*/

    public int getBatchId() {
        return batchId;
    }

    // Method to update the assigned delivery person in the database
    /*private void updateDeliveryPersonInDatabase(dbConnector dbConnection) {
        String query = "UPDATE DeliveryBatches SET delivery_person_id = ? WHERE batch_id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, deliveryPerson.getPersonId());
            statement.setInt(2, batchId);

            statement.executeUpdate(); // Execute the update

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
}

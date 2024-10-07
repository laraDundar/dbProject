package pizzaSoftware;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Delivery {
    private String zipCode;
    private Timestamp startTime;
    private int orderId; // Assuming this refers to the order being delivered
    private String status; // e.g., "Pending", "In Transit", "Delivered"
    
    public Delivery(String zipCode, Order order) {
        this.zipCode = zipCode;
        this.orderId = order.getOrderId();
        LocalDateTime currentTime = LocalDateTime.now().minusMinutes(5); // Subtract 5 minutes
        this.startTime = Timestamp.valueOf(currentTime.plusMinutes(order.getEstimatedPreparationTime())); // Add estimated preparation time
        
        this.status = "Pending"; // Initial status
    }

    // Method to retrieve delivery information from the database
    public void retrieveFromDatabase(dbConnector dbConnection, int orderId) {
        String query = "SELECT * FROM deliveries WHERE order_id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, orderId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    this.zipCode = resultSet.getString("zip_code");
                    this.startTime = resultSet.getTimestamp("start_time");
                    this.orderId = resultSet.getInt("order_id");
                    this.status = resultSet.getString("status");
                } else {
                    System.out.println("No delivery found for the given orderId.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update delivery status in the database
    public void updateStatusInDatabase(dbConnector dbConnection, String newStatus) {
        String query = "UPDATE deliveries SET status = ? WHERE order_id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, newStatus);
            statement.setInt(2, orderId);

            statement.executeUpdate(); // Execute the update

            // Update the status in the object as well
            this.status = newStatus;

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Getters and Setters
    public String getZipCode() {
        return zipCode;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStatus(String status, dbConnector dbConnection) {
        updateStatusInDatabase(dbConnection, status); // Update the status in the DB
    }
}
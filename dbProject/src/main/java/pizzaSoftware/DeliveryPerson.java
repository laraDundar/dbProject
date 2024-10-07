package pizzaSoftware;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DeliveryPerson {
    private int deliveryPersonId;
    private String name;
    private int areaId; // Area they are assigned to
    private boolean availabilityStatus; // true if available, false otherwise
    private LocalDateTime unavailableUntil; // Track when they become available again
    private DeliveryArea deliveryArea;

    public DeliveryPerson(int deliveryPersonId, String name, int areaId) {
        this.deliveryPersonId = deliveryPersonId;
        this.name = name;
        this.areaId = areaId;
        this.availabilityStatus = true; // Initially available
    }

    // Constructor to retrieve a DeliveryPerson from the database
    public DeliveryPerson(int deliveryPersonId, dbConnector dbConnection) {
        retrieveFromDatabase(deliveryPersonId, dbConnection);
    }

    // Method to set delivery person as unavailable for a specific time
    public void setUnavailable(int minutes, dbConnector dbConnection) {
        this.availabilityStatus = false;
        this.unavailableUntil = LocalDateTime.now().plusMinutes(minutes);
        updateAvailabilityStatusInDatabase(dbConnection);
    }

    public void setUnavailable(int minutes) {
        this.availabilityStatus = false;
        this.unavailableUntil = LocalDateTime.now().plusMinutes(minutes);
    }

    public boolean isAvailable() {
        // Check if the delivery person is currently available
        if (!availabilityStatus && LocalDateTime.now().isAfter(unavailableUntil)) {
            availabilityStatus = true; // They are now available
        }
        return availabilityStatus;
    }

    // Method to retrieve delivery person details from the database
    private void retrieveFromDatabase(int deliveryPersonId, dbConnector dbConnection) {
        String query = "SELECT * FROM delivery_people WHERE delivery_person_id = ?";
        
        try (Connection connection = dbConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setInt(1, deliveryPersonId);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    this.deliveryPersonId = resultSet.getInt("delivery_person_id");
                    this.name = resultSet.getString("name");
                    this.areaId = resultSet.getInt("area_id");
                    this.availabilityStatus = resultSet.getBoolean("availability_status");
                    //this.unavailableUntil = resultSet.getTimestamp("unavailable_until").toLocalDateTime();
                    //this.deliveryArea = new DeliveryArea(areaId, dbConnection); // Assuming a DeliveryArea constructor that retrieves data from DB
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update the delivery person's availability status in the database
    private void updateAvailabilityStatusInDatabase(dbConnector dbConnection) {
        String query = "UPDATE delivery_people SET availability_status = ? WHERE delivery_person_id = ?";
        
        try (Connection connection = dbConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setBoolean(1, availabilityStatus);
            //statement.setTimestamp(2, Timestamp.valueOf(unavailableUntil));
            statement.setInt(3, deliveryPersonId);
            
            statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Getters
    public int getDeliveryPersonId() {
        return deliveryPersonId;
    }

    public String getName() {
        return name;
    }

    public int getAreaId() {
        return areaId;
    }

    public DeliveryArea getDeliveryArea(int deliveryPersonId){
        return deliveryArea;
    }

    public DeliveryArea getDeliveryArea(dbConnector dbConnection) {
        return deliveryArea;
    }

    // Method to assign a delivery area and update it in the database
    public void setDeliveryArea(DeliveryArea deliveryArea, dbConnector dbConnection) {
        this.deliveryArea = deliveryArea;
        this.areaId = deliveryArea.getAreaId();
        updateAreaInDatabase(dbConnection);
    }

    // Method to update delivery area in the database
    private void updateAreaInDatabase(dbConnector dbConnection) {
        String query = "UPDATE delivery_people SET area_id = ? WHERE delivery_person_id = ?";
        
        try (Connection connection = dbConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setInt(1, areaId);
            statement.setInt(2, deliveryPersonId);
            
            statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

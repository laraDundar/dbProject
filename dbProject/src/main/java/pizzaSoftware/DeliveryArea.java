package pizzaSoftware;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeliveryArea {
    private int areaId; // Unique identifier for the area
    private String zipCode; // Postal code for this area
    private int distance; // Distance from the restaurant (1 = close, 2 = far, 3 = very far)

    public DeliveryArea(int areaId, String zipCode, int distance) {
        this.areaId = areaId;
        this.zipCode = zipCode;
        this.distance = distance;
    }

    // Constructor to create a DeliveryArea object from database data
    public DeliveryArea(String zipCode, dbConnector dbConnection) {
        retrieveFromDatabase(dbConnection, zipCode);
    }

    // Method to save the DeliveryArea to the database
    public void saveToDatabase(dbConnector dbConnection) {
        String query = "INSERT INTO DeliveryAreas (area_id, zip_code, distance) VALUES (?, ?, ?)";

        try (Connection connection = dbConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, areaId);
            statement.setString(2, zipCode);
            statement.setInt(3, distance);

            statement.executeUpdate(); // Execute the insert
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve DeliveryArea data from the database using the zip code
    public void retrieveFromDatabase(dbConnector dbConnection, String zipCode) {
        String query = "SELECT * FROM DeliveryAreas WHERE zip_code = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, zipCode);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    this.areaId = resultSet.getInt("area_id");
                    this.zipCode = resultSet.getString("zip_code");
                    this.distance = resultSet.getInt("distance");
                } else {
                    System.out.println("No delivery area found for the given zip code.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update DeliveryArea information in the database
    public void updateInDatabase(dbConnector dbConnection) {
        String query = "UPDATE DeliveryAreas SET distance = ? WHERE zip_code = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, distance);
            statement.setString(2, zipCode);

            statement.executeUpdate(); // Execute the update
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Getters
    public int getAreaId() {
        return areaId;
    }


    public String getZipCode() {
        return zipCode;
    }

    public int getDistance() {
        return distance;
    }

    // Setters with database interaction
    public void setDistance(int distance, dbConnector dbConnection) {
        this.distance = distance;
        updateInDatabase(dbConnection); // Update the database when distance is changed
    }

    public void setZipCode(String zipCode, dbConnector dbConnection) {
        this.zipCode = zipCode;
        updateInDatabase(dbConnection); // Update the database when zip code is changed
    }
}
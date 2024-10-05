package pizzaSoftware;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuService {
    private dbConnector dbConnector;

    /**
     * i connect to the DB
     */
    public MenuService() {
        dbConnector = new dbConnector();
    }

    public dbConnector getdbConnector(){
        return dbConnector;
    }

    /**
     * 
     * @param pizzaId
     * @return all the details about a pizza with the given ID
     */
    public Pizza getPizzaById(int pizza_id) {
        
        String query = "SELECT * FROM Pizzas WHERE pizza_id = ?";
        try (Connection conn = dbConnector.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, pizza_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Pizza(rs.getInt("pizza_id"), rs.getString("pizza_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param drinkId
     * @return all the details about a drink with the given ID
     */
    public Drink getDrinkById(int drink_id) {
        // Query to get drink details by ID
        String query = "SELECT * FROM Drinks WHERE drink_id = ?";
        try (Connection conn = dbConnector.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, drink_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Drink(rs.getInt("drink_id"), rs.getString("name"), rs.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param dessertId
     * @return all the details about a dessert with the given ID
     */
    public Dessert getDessertById(int dessert_id) {
        // Query to get dessert details by ID
        String query = "SELECT * FROM Desserts WHERE dessert_id = ?";
        try (Connection conn = dbConnector.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, dessert_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Dessert(rs.getInt("dessert_id"), rs.getString("name"), rs.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

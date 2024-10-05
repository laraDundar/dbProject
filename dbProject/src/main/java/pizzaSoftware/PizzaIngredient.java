package pizzaSoftware;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PizzaIngredient {
    private int pizzaIngredientId;
    private int pizzaId;
    private Ingredient ingredient;
    private double quantity;

    //this is ok
    //getters and setters for pizzaIngredient

    public PizzaIngredient(Ingredient ingredient, int pizzaId, double quantity) {
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.pizzaId = pizzaId;
    }

    public void setQuantity (double quantity){
        this.quantity = quantity;
    }
    
    public double getQuantity(){
        return quantity;
    }

    public int getPizzaIngredientId() {
        return pizzaIngredientId;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setPizzaIngredientId(int pizzaIngredientId) {
        this.pizzaIngredientId = pizzaIngredientId;
    }

    public int getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(int pizzaId) {
        this.pizzaId = pizzaId;
    }

    // Method to calculate the total price of this ingredient based on its quantity
    public double getPriceIngredientPerQuantity() {
        return ingredient.getPrice() * quantity; // Price of ingredient times its quantity
    }


   /**
     * Method to retrieve ingredients for a pizza from the database by pizzaId
     * @param pizzaId the ID of the pizza
     * @return List of PizzaIngredient objects
     */
    public static List<PizzaIngredient> getIngredientsByPizzaId(int pizzaId) {
        List<PizzaIngredient> ingredients = new ArrayList<>();
        dbConnector connector = new dbConnector();
        Connection connection = connector.connect();
        
        String sql = "SELECT pi.pizza_id, i.ingredient_id, i.ingredient_name, i.is_vegetarian, i.is_vegan, " +
                     "pi.quantity, i.cost " +
                     "FROM pizza_ingredients pi " +
                     "JOIN Ingredients i ON pi.ingredient_id = i.ingredient_id " +
                     "WHERE pi.pizza_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pizzaId);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                // Retrieve data from the result set
                int ingredientId = resultSet.getInt("ingredient_id");
                String name = resultSet.getString("ingredient_name");
                boolean isVegetarian = resultSet.getBoolean("is_vegetarian");
                boolean isVegan = resultSet.getBoolean("is_vegan");
                double quantity = resultSet.getDouble("quantity");
                double pricePerUnit = resultSet.getDouble("cost");

                // Create Ingredient and PizzaIngredient objects
                Ingredient ingredient = new Ingredient(ingredientId, name, pricePerUnit, isVegetarian, isVegan);
                PizzaIngredient pizzaIngredient = new PizzaIngredient(ingredient, pizzaId, quantity);
                
                // Add the pizza ingredient to the list
                ingredients.add(pizzaIngredient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error retrieving ingredients from the database: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close(); // Close the connection after use
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return ingredients;
    }
}

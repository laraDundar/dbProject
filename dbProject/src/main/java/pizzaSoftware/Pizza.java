package pizzaSoftware;
import java.util.List;

public class Pizza {
    private int pizzaId;
    private String pizzaName;
   
    /**
     * constructor for a pizza
     * @param pizzaId
     * @param name
     */
    public Pizza(int pizzaId, String pizzaName) {
        this.pizzaId = pizzaId;
        this.pizzaName = pizzaName;
    }

    //getters and setters for pizza

    public int getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(int pizzaId) {
        this.pizzaId = pizzaId;
    }

    public String getPizzaName() {
        return pizzaName;
    }

    public void setPizzaName(String pizzaName) {
        this.pizzaName = pizzaName;
    }

    /**
     * Method to retrieve ingredients for the pizza by querying the PizzaIngredient class
     * @return List of ingredients used in this pizza
     */
    public List<PizzaIngredient> getIngredients() {
        // This would usually query your database or system to get ingredients for this pizza
        return PizzaIngredient.getIngredientsByPizzaId(pizzaId);
    }

    /**
     * Method to calculate the price of the pizza based on its ingredients
     * @return The total price of the pizza
     */
    public double getPrice() {
        List<PizzaIngredient> ingredients = getIngredients();  // Get ingredients dynamically
        double totalIngredientPrice = 0.0;

        for (PizzaIngredient pizzaIngredient : ingredients) {
            totalIngredientPrice += pizzaIngredient.getPriceIngredientPerQuantity(); // Use the total price from PizzaIngredient
        }

        double profitMargin = 0.2; // 20% profit margin
        return totalIngredientPrice * (1 + profitMargin); // Calculate the final price
    }

    /**
     * Method to check if pizza is vegetarian (assuming Ingredient has a method isVegetarian)
     * @return true if the pizza is vegetarian, false otherwise
     */
    public boolean isVegetarian() {
        List<PizzaIngredient> ingredients = getIngredients();
        for (PizzaIngredient pizzaIngredient : ingredients) {
            if (!pizzaIngredient.getIngredient().isVegetarian()) {
                return false; // If any ingredient is non-vegetarian, the pizza is not vegetarian
            }
        }
        return true;
    }

    /**
     * Method to check if pizza is vegan (assuming Ingredient has a method isVegan)
     * @return true if the pizza is vegan, false otherwise
     */
    public boolean isVegan() {
        List<PizzaIngredient> ingredients = getIngredients();
        for (PizzaIngredient pizzaIngredient : ingredients) {
            if (!pizzaIngredient.getIngredient().isVegan()) {
                return false; // If any ingredient is non-vegan, the pizza is not vegan
            }
        }
        return true;
    }
    
}

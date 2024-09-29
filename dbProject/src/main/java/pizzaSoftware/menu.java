package pizzaSoftware;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class menu {

    private Connection connection;

    public menu(Connection connection) {
        this.connection = connection;
    }

    public List<Pizza> getPizzas() {
        List<Pizza> pizzas = new ArrayList<>();
        String sqlQuery = "SELECT * FROM Pizzas";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {

            while (resultSet.next()) {
                Pizza pizza = new Pizza();
                pizza.setPizzaId(resultSet.getInt("pizza_id"));
                pizza.setPizzaName(resultSet.getString("pizza_name"));
                pizza.setPrice(resultSet.getDouble("pizza_price"));
                pizza.setVegetarian(resultSet.getBoolean("is_vegetarian"));
                pizza.setVegan(resultSet.getBoolean("is_vegan"));
                
                pizzas.add(pizza);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pizzas;
    }

    public List<Drink> getDrinks() {
        List<Drink> drinks = new ArrayList<>();
        String sqlQuery = "SELECT * FROM Drinks";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {

            while (resultSet.next()) {
                Drink drink = new Drink();
                drink.setDrinkId(resultSet.getInt("drink_id"));
                drink.setDrinkName(resultSet.getString("name"));
                drink.setPrice(resultSet.getDouble("price"));
                
                drinks.add(drink);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drinks;
    }

    public List<Dessert> getDesserts() {
        List<Dessert> desserts = new ArrayList<>();
        String sqlQuery = "SELECT * FROM Desserts";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {

            while (resultSet.next()) {
                Dessert dessert = new Dessert();
                dessert.setDessertId(resultSet.getInt("dessert_id"));
                dessert.setDessertName(resultSet.getString("name"));
                dessert.setPrice(resultSet.getDouble("price"));
                
                desserts.add(dessert);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return desserts;
    }

    public List<Ingredient> getPizzaIngredients(int pizzaId) {
        List<Ingredient> ingredients = new ArrayList<>();
        String sqlQuery = "SELECT i.* FROM Ingredients i JOIN PizzaIngredients pi ON i.ingredientId = pi.ingredientId WHERE pi.pizzaId = " + pizzaId;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {

            while (resultSet.next()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setIngredientId(resultSet.getInt("ingredientId"));
                ingredient.setIngredientName(resultSet.getString("ingredientName"));
                ingredient.setCost(resultSet.getDouble("cost"));
                ingredient.setVegetarian(resultSet.getBoolean("isVegetarian"));
                ingredient.setVegan(resultSet.getBoolean("isVegan"));
                
                ingredients.add(ingredient);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    public void displayMenu() {

        System.out.println("Pizzas:");
        for (Pizza pizza : getPizzas()) {
            System.out.println(pizza.getPizzaName() + " - $" + pizza.getPrice());
            System.out.println("Ingredients:");
            for (Ingredient ingredient : getPizzaIngredients(pizza.getPizzaId())) {
                System.out.println("- " + ingredient.getIngredientName());
            }
        }

        System.out.println("\nDrinks:");
        for (Drink drink : getDrinks()) {
            System.out.println(drink.getDrinkName() + " - $" + drink.getPrice());
        }

        System.out.println("\nDesserts:");
        for (Dessert dessert : getDesserts()) {
            System.out.println(dessert.getDessertName() + " - $" + dessert.getPrice());
        }
    }
    
}

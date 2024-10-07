package pizzaSoftware;

import java.sql.Connection;
import java.util.List;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;

import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class menu {

    private Connection connection;
    private MenuService menuService;
    private cart Cart;

    public menu() {
        this.menuService = new MenuService();
        this.Cart = new cart(); 
    }

    public menu(Connection connection) {
        this.connection = connection;
        this.menuService = new MenuService();
        this.Cart = new cart();
    }

    // Method to set the Connection object
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }
    
    @FXML
    private Label pizzaMenuLabel;

    @FXML
    private TableView<Pizza> pizzaMenuTable;

    @FXML
    private TableView<Drink> drinksMenuTable;

    @FXML
    private TableView<Dessert> dessertsMenuTable;

    @FXML
    private Button cartButton;

    @FXML
    void orderAction(ActionEvent event) {}

    @FXML
    void cartButtonAction(ActionEvent event) {
        try {
            // Load the FXML file from the resources directory
            URL resource = getClass().getResource("/cartPage.fxml"); // Use leading slash for root resources
            System.out.println("Resource URL: " + resource); // Debugging: Print the resource URL
            
            if (resource == null) {
                throw new IllegalArgumentException("FXML file not found!");
            }
    
            FXMLLoader loader = new FXMLLoader(resource);
            Parent cartPage = loader.load();
    
            // Get the current stage
            Stage stage = (Stage) cartButton.getScene().getWindow();
    
            // Set the scene with the new cart page
            stage.setScene(new Scene(cartPage));
            stage.show();
            
            // Optionally, you can set the controller if you need to pass data
            cartController controller = loader.getController();
            //controller.initialize(); // Call any initialization methods if needed
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load cart page");
            alert.setContentText("Please check the FXML file or the path.");
            alert.showAndWait();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Resource not found");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void initialize() {
        setupPizzaTable();
        setupDrinkTable();
        setupDessertTable();
        loadMenuData();

        pizzaMenuTable.setOnMouseClicked(event -> {
            Pizza selectedPizza = pizzaMenuTable.getSelectionModel().getSelectedItem();
            
            if (event.getClickCount() == 2) {
                if (selectedPizza != null) {
                    addPizzaToCart(selectedPizza); // Add pizza to cart on double-click
                }
            }
        });

        drinksMenuTable.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2) {
                Drink selectedDrink = drinksMenuTable.getSelectionModel().getSelectedItem();
                if (selectedDrink != null) {
                    addDrinkToCart(selectedDrink);
            }
        }
    });
        dessertsMenuTable.setOnMouseClicked(event -> {
        if(event.getClickCount() == 2) {
            Dessert selectedDessert = dessertsMenuTable.getSelectionModel().getSelectedItem();
                if (selectedDessert != null) {
                    addDessertToCart(selectedDessert);
            }
        }
    });
    }

    private void showIngredients(Pizza pizza) {
    List<PizzaIngredient> ingredients = pizza.getIngredients();
    StringBuilder ingredientsList = new StringBuilder("Ingredients for " + pizza.getPizzaName() + ":\n");

    for (PizzaIngredient pizzaIngredient : ingredients) {
        ingredientsList.append("- ")
                .append(pizzaIngredient.getIngredient().getIngredientName())
                .append("\n");
    }

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Pizza Ingredients");
    alert.setHeaderText("Ingredients for " + pizza.getPizzaName());
    alert.setContentText(ingredientsList.toString());
    alert.showAndWait();
}

    private void setupPizzaTable() {
        
        TableColumn<Pizza, String> pizzaNameColumn = new TableColumn<>("Pizza Name");
        pizzaNameColumn.setCellValueFactory(new PropertyValueFactory<>("pizzaName")); // Matches the getPizzaName() method
    
        TableColumn<Pizza, Double> pizzaPriceColumn = new TableColumn<>("Price");
        pizzaPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price")); // Looks for getPrice()

        TableColumn<Pizza, Boolean> vegetarianColumn = new TableColumn<>("Vegetarian");
        vegetarianColumn.setCellValueFactory(cellData -> {
            Pizza pizza = cellData.getValue();
            return new SimpleBooleanProperty(pizza.isVegetarian());
        });

        TableColumn<Pizza, Boolean> veganColumn = new TableColumn<>("Vegan");
        veganColumn.setCellValueFactory(cellData -> {
            Pizza pizza = cellData.getValue();
            // Ensure that pizza is fetched from the database before checking
            return new SimpleBooleanProperty(pizza.isVegan()); // Call isVegan() which fetches the ingredients
        });

        TableColumn<Pizza, String> ingredientsColumn = new TableColumn<>("Ingredients");
        ingredientsColumn.setCellValueFactory(cellData -> {
        Pizza pizza = cellData.getValue();
        StringBuilder ingredientsList = new StringBuilder();
        for (PizzaIngredient ingredient : pizza.getIngredients()) {
            ingredientsList.append(ingredient.getIngredient().getIngredientName()).append(", ");
        }
        // Remove the trailing comma and space, if any
        if (ingredientsList.length() > 0) {
            ingredientsList.setLength(ingredientsList.length() - 2);
        }
        return new SimpleStringProperty(ingredientsList.toString());
    });

    pizzaMenuTable.getColumns().addAll(pizzaNameColumn, pizzaPriceColumn, vegetarianColumn, veganColumn, ingredientsColumn);
}


    private void setupDrinkTable() {
        TableColumn<Drink, String> drinkNameColumn = new TableColumn<>("Drink Name");
        drinkNameColumn.setCellValueFactory(new PropertyValueFactory<>("drinkName")); // Matches getDrinkName()

        TableColumn<Drink, Double> drinkPriceColumn = new TableColumn<>("Price");
        drinkPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price")); // Looks for getPrice()

        drinksMenuTable.getColumns().addAll(drinkNameColumn, drinkPriceColumn);
    }

    private void setupDessertTable() {
        TableColumn<Dessert, String> dessertNameColumn = new TableColumn<>("Dessert Name");
        dessertNameColumn.setCellValueFactory(new PropertyValueFactory<>("dessertName")); // Matches getDessertName()

        TableColumn<Dessert, Double> dessertPriceColumn = new TableColumn<>("Price");
        dessertPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price")); // Looks for getPrice()

        dessertsMenuTable.getColumns().addAll(dessertNameColumn, dessertPriceColumn);
    }

    private void loadMenuData() {
        List<Pizza> pizzas = menuService.getPizzas();
        pizzaMenuTable.getItems().addAll(pizzas);

        List<Drink> drinks = menuService.getDrinks();
        drinksMenuTable.getItems().addAll(drinks);

        List<Dessert> desserts = menuService.getDesserts();
        dessertsMenuTable.getItems().addAll(desserts);
    }

    private void addPizzaToCart(Pizza pizza) {
        cart Cart = cart.getInstance(); 
        Cart.addPizza(pizza); // Add selected pizza to cart
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pizza Added");
        alert.setHeaderText("Added to Cart");
        alert.setContentText(pizza.getPizzaName() + " has been added to your cart.");
        alert.showAndWait();
    }

    private void addDrinkToCart(Drink drink) {
        cart Cart = cart.getInstance();
        Cart.addDrink(drink); // Add selected drink to cart
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Drink Added");
        alert.setHeaderText("Added to Cart");
        alert.setContentText(drink.getDrinkName() + " has been added to your cart.");
        alert.showAndWait();
    }

    private void addDessertToCart(Dessert dessert) {
        cart Cart = cart.getInstance();
        Cart.addDessert(dessert); // Add selected dessert to cart
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dessert Added");
        alert.setHeaderText("Added to Cart");
        alert.setContentText(dessert.getDessertName() + " has been added to your cart.");
        alert.showAndWait();
    }
    
}

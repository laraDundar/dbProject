package pizzaSoftware;

import java.sql.Connection;
import java.util.List;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class menu {

    private Connection connection;
    private MenuService menuService;

    public menu() {
        this.menuService = new MenuService();
    }

    public menu(Connection connection) {
        this.connection = connection;
        this.menuService = new MenuService();
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
    private Button orderButton;

    @FXML
    void orderAction(ActionEvent event) {}

    @FXML
    void cartButtonAction(ActionEvent event) {}

    @FXML
    public void initialize() {
        setupPizzaTable();
        setupDrinkTable();
        setupDessertTable();
        loadMenuData();

            
        pizzaMenuTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { 
                Pizza selectedPizza = pizzaMenuTable.getSelectionModel().getSelectedItem();
            if (selectedPizza != null) {
                showIngredients(selectedPizza);
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

        pizzaMenuTable.getColumns().addAll(pizzaNameColumn, pizzaPriceColumn, vegetarianColumn, veganColumn);

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

    
}

package pizzaSoftware;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class cartController {
    @FXML
    private Label totalPriceLabel;

    @FXML
    private TableView<Dessert> checkoutCartTableDesserts;

    @FXML
    private Label yourOrderLabel;

    @FXML
    private Label tenPizzaDiscountLabel;

    @FXML
    private Label discountsLabel;

    @FXML
    private Label birthdayDiscountLabel;

    @FXML
    private TableView<Pizza> checkoutCartTablePizza;

    @FXML
    private TableView<Drink> checkoutCartTableDrinks;

    @FXML
    private Button proceedCheckoutButton;

    @FXML
    private Button backToMenuButton;

    @FXML
    private Label yourCartLabel;

    @FXML
    public void initialize() {
        calculateTotalPrice();
        // Initialize Pizza TableView
        TableColumn<Pizza, String> pizzaNameColumn = new TableColumn<>("Pizza Name");
        pizzaNameColumn.setCellValueFactory(new PropertyValueFactory<>("pizzaName"));
        
        TableColumn<Pizza, Double> pizzaPriceColumn = new TableColumn<>("Price");
        pizzaPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        checkoutCartTablePizza.getColumns().addAll(pizzaNameColumn, pizzaPriceColumn);
        checkoutCartTablePizza.setItems(FXCollections.observableArrayList(cart.pizzasInCart));
        
        // Initialize Drink TableView
        TableColumn<Drink, String> drinkNameColumn = new TableColumn<>("Drink Name");
        drinkNameColumn.setCellValueFactory(new PropertyValueFactory<>("drinkName"));
        
        TableColumn<Drink, Double> drinkPriceColumn = new TableColumn<>("Price");
        drinkPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        checkoutCartTableDrinks.getColumns().addAll(drinkNameColumn, drinkPriceColumn);
        checkoutCartTableDrinks.setItems(FXCollections.observableArrayList(cart.drinksInCart));
        
        // Initialize Dessert TableView
        TableColumn<Dessert, String> dessertNameColumn = new TableColumn<>("Dessert Name");
        dessertNameColumn.setCellValueFactory(new PropertyValueFactory<>("dessertName"));
        
        TableColumn<Dessert, Double> dessertPriceColumn = new TableColumn<>("Price");
        dessertPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        checkoutCartTableDesserts.getColumns().addAll(dessertNameColumn, dessertPriceColumn);
        checkoutCartTableDesserts.setItems(FXCollections.observableArrayList(cart.dessertsInCart));
    }

    @FXML
    void proceedCheckoutAction(ActionEvent event) {

    }

    @FXML
    void backToMenuAction(ActionEvent event) {

    }

    private void calculateTotalPrice() {
        double totalPrice = 0.0;
    
        for (Pizza pizza : cart.pizzasInCart) {
            totalPrice += pizza.getPrice();
        }
        for (Drink drink : cart.drinksInCart) {
            totalPrice += drink.getPrice();
        }
        for (Dessert dessert : cart.dessertsInCart) {
            totalPrice += dessert.getPrice();
        }
    
        // Set the total price in the label
        totalPriceLabel.setText(String.format("Total: €%.2f", totalPrice));
    }
}

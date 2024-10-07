package pizzaSoftware;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;

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
        TableColumn<Pizza, String> pizzaNameColumn = new TableColumn<>("Pizza Name");
        pizzaNameColumn.setCellValueFactory(new PropertyValueFactory<>("pizzaName"));

        TableColumn<Pizza, Double> pizzaPriceColumn = new TableColumn<>("Price");
        pizzaPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        checkoutCartTablePizza.getColumns().addAll(pizzaNameColumn, pizzaPriceColumn);

        // Setup Drinks TableView
        TableColumn<Drink, String> drinkNameColumn = new TableColumn<>("Drink Name");
        drinkNameColumn.setCellValueFactory(new PropertyValueFactory<>("drinkName"));

        TableColumn<Drink, Double> drinkPriceColumn = new TableColumn<>("Price");
        drinkPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        checkoutCartTableDrinks.getColumns().addAll(drinkNameColumn, drinkPriceColumn);

        // Setup Desserts TableView
        TableColumn<Dessert, String> dessertNameColumn = new TableColumn<>("Dessert Name");
        dessertNameColumn.setCellValueFactory(new PropertyValueFactory<>("dessertName"));

        TableColumn<Dessert, Double> dessertPriceColumn = new TableColumn<>("Price");
        dessertPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        checkoutCartTableDesserts.getColumns().addAll(dessertNameColumn, dessertPriceColumn);

        loadCartData();
    }

    @FXML
    void proceedCheckoutAction(ActionEvent event) {

    }

    @FXML
    void backToMenuAction(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainPage.fxml"));
        Parent mainPage = loader.load();
        Stage stage = (Stage) backToMenuButton.getScene().getWindow();
        stage.setScene(new Scene(mainPage));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }

    }

    private double calculateTotalPrice(cart Cart) {
        double totalPrice = 0.0;
    
        for (Pizza pizza : Cart.pizzasInCart) {
            totalPrice += pizza.getPrice();
        }
        for (Drink drink : Cart.drinksInCart) {
            totalPrice += drink.getPrice();
        }
        for (Dessert dessert : Cart.dessertsInCart) {
            totalPrice += dessert.getPrice();
        }
        return totalPrice;
    }

    private void loadCartData() {
        cart cartInstance = cart.getInstance(); // Get the singleton instance

        checkoutCartTablePizza.getItems().setAll(cartInstance.getPizzasInCart());
        checkoutCartTableDrinks.getItems().setAll(cartInstance.getDrinksInCart());
        checkoutCartTableDesserts.getItems().setAll(cartInstance.getDessertsInCart());
        
        double totalPrice = calculateTotalPrice(cartInstance);
        totalPriceLabel.setText("Total Price: €" + String.format("%.2f", totalPrice));
    }
}

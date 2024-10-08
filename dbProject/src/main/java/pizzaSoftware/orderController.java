package pizzaSoftware;

import java.sql.Connection;
import java.util.List;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class orderController {

    @FXML
    private Label orderPageTitle;

    @FXML
    private Label discountAppliedLabel;

    @FXML
    private Button cancelOrderButton;

    @FXML
    private Label birthdayDiscountLabel;

    @FXML
    private Label loyaltyDiscountLabel;

    @FXML
    private Label statusShowingLabel;

    @FXML
    private Label orderStatusLabel;

    @FXML
    void cancelOrderAction(ActionEvent event) {}

    @FXML
    public void initialize() throws SQLException {
        placeOrderController();
    }

    private OrderService orderService;
    private cart cartInstance;
    private Connection connection;

    public orderController(Connection connection) {
        orderService = new OrderService();
        cartInstance = cart.getInstance();
        this.connection = connection;
    }

    // Method to place an order from the cart using the logged-in customer.
    public void placeOrderController() throws SQLException {
        // Retrieve the logged-in customer from SessionManager.
        Customer loggedInCustomer = SessionManager.getInstance().getLoggedInCustomer();
        
        if (loggedInCustomer == null) {
            throw new IllegalStateException("No customer is currently logged in.");
        }

        // Fetch items from the cart.
        List<Pizza> pizzasInCart = cartInstance.getPizzasInCart();
        List<Drink> drinksInCart = cartInstance.getDrinksInCart();
        List<Dessert> dessertsInCart = cartInstance.getDessertsInCart();

        // Convert the cart items to their respective ID lists.
        List<Integer> pizzaIds = pizzasInCart.stream()
                                             .map(Pizza::getPizzaId)
                                             .toList();
        List<Integer> drinkIds = drinksInCart.stream()
                                             .map(Drink::getDrinkId)
                                             .toList();
        List<Integer> dessertIds = dessertsInCart.stream()
                                                 .map(Dessert::getDessertId)
                                                 .toList();
        System.out.println("Pizza IDs: " + pizzaIds);

        System.out.println("Drink IDs: " + drinkIds);
                                                 
        System.out.println("Dessert IDs: " + dessertIds);

        // Place the order via OrderService.
        Order newOrder = orderService.placeOrder(loggedInCustomer, pizzaIds, drinkIds, dessertIds);

        // Clear the cart after placing the order.
        clearCart();
    }

    // Clear the cart after placing the order.
    private void clearCart() {
        cartInstance.pizzasInCart.clear();
        cartInstance.drinksInCart.clear();
        cartInstance.dessertsInCart.clear();
        System.out.println("Cart has been cleared after placing the order.");
    }
}

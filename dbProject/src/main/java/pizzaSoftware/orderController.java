package pizzaSoftware;

import java.sql.Connection;
import java.util.List;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class orderController {

    private Timer cancellationTimer;
    private boolean canCancelOrder = true;

    @FXML
    private Label orderPageTitle;

    @FXML
    private Button cancelOrderButton;

    @FXML
    private Label statusShowingLabel;

    @FXML
    private Label orderStatusLabel;

    @FXML
    void cancelOrderAction(ActionEvent event) {
        if (canCancelOrder) {
            // Proceed with cancellation
            Order currentOrder = SessionManager.getInstance().getCurrentOrder();
            if (currentOrder != null) {
                boolean success = orderService.cancelOrder(currentOrder.getOrderId());
                if (success) {
                    showAlert("Order Cancelled", "Your order has been successfully cancelled.");
                    clearCart();
                    cancelTimer(); // Cancel the timer after successful cancellation
                } else {
                    showAlert("Cancellation Failed", "Unable to cancel the order. Please try again.");
                }
            } else {
                showAlert("Order Error", "No order found to cancel.");
            }
        } else {
            showAlert("Cancellation Failed", "The 5-minute window for order cancellation has expired.");
        }
    }

    @FXML
    public void initialize() throws SQLException {
        Customer loggedInCustomer = SessionManager.getInstance().getLoggedInCustomer();
        System.out.println("OrderController has been initialized");
        placeOrderController();
    }

    private OrderService orderService;
    private cart cartInstance;
    private Connection connection;

    public orderController() {
        orderService = new OrderService();
        cartInstance = cart.getInstance();
        connection = null;
    }

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

        System.out.println("Pizzas in Cart: " + pizzasInCart.size());
        System.out.println("Drinks in Cart: " + drinksInCart.size());
        System.out.println("Desserts in Cart: " + dessertsInCart.size());

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

        if (newOrder != null && newOrder.getOrderId() != 0) {
            // Store the new order in SessionManager for later use (e.g., for cancellation)
            SessionManager.getInstance().setCurrentOrder(newOrder);
            // Start the 5-minute cancellation timer
            startCancellationTimer();
            System.out.println("Order placed and stored in SessionManager with order ID: " + newOrder.getOrderId());
        }

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

    public void showAlert(String title, String message) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
    }

    private void startCancellationTimer() {
        cancellationTimer = new Timer();

        // Schedule the task to disable order cancellation after 5 minutes
        cancellationTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                canCancelOrder = false; // Disable order cancellation after 5 minutes
                System.out.println("Cancellation window has expired.");
            }
        }, TimeUnit.MINUTES.toMillis(5)); // Delay of 5 minutes
    }

    private void cancelTimer() {
        if (cancellationTimer != null) {
            cancellationTimer.cancel();
            System.out.println("Cancellation timer stopped.");
        }
    }
}

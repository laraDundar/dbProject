package pizzaSoftware;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

public class loginPageController {
    @FXML
    private Label loginPageTitleLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button signInButton;
    @FXML
    private Button createAccountButton;

    private LoginManager loginManager;

    private dbConnector connector;

    private static final String OWNER_USERNAME = "maria";
    private static final String OWNER_PASSWORD = "12345";

    public loginPageController() {
        loginManager = new LoginManager();
        connector = new dbConnector();
    }
    
    private Connection getConnection() {
        return connector.connect();
    }

    @FXML
    public void signInAction(ActionEvent event) {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Login Error", "Please fill in both username and password.");
            return;
        }

        if (username.equals(OWNER_USERNAME) && password.equals(OWNER_PASSWORD)) {
            showAlert("Login Success", "Welcome, Pizzeria Owner!");
            loadOwnerPage();
            return;
        }

        boolean success = loginManager.login(username, password);

        Customer loggededInCustomer = new Customer();
        loggededInCustomer.retrieveCustomerByUsername(connector, username);
        System.out.println("Customer ID after retrieval: " + loggededInCustomer.getCustomerId());
        SessionManager.getInstance().setLoggedInCustomer(loggededInCustomer);
        loadMainPage();
        if (success) {
            showAlert("Login Success", "Welcome, " + username + "!");

            Customer loggedInCustomer = new Customer();
            loggedInCustomer.retrieveCustomerByUsername(connector, username);
        
            if (loggedInCustomer.getCustomerId() != 0) { // Ensure that a valid customer is retrieved.
                SessionManager.getInstance().setLoggedInCustomer(loggedInCustomer);
                loadMainPage();
            } else {
                showAlert("Login Failed", "Customer not found. Please try again.");
            }

            loadMainPage();
        } else {
            showAlert("Login Failed", "Invalid username or password. Please try again.");
        }
    }

    @FXML
    public void createAccountAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/signupPage.fxml"));
            Parent signupPageRoot = loader.load();

            Stage stage = (Stage) createAccountButton.getScene().getWindow();
            stage.setScene(new Scene(signupPageRoot));
            stage.setTitle("Create an Account");

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Unable to load the signup page.");
        }
    }

    private void loadMainPage() {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainPage.fxml"));
        Parent mainPageRoot = loader.load();

        // Retrieve the controller
        menu menuController = loader.getController();

        // Assuming you have a method to get your connection object
        Connection connection = getConnection(); // Replace this with your actual connection retrieval logic

        // Pass the connection to the menu controller
        menuController.setConnection(connection);
        menuController.setMenuService(new MenuService());

        Stage stage = (Stage) signInButton.getScene().getWindow();
        stage.setScene(new Scene(mainPageRoot));
        stage.setTitle("Main Page");

        } catch (IOException e) {
        e.printStackTrace();
        showAlert("Navigation Error", "Unable to load the main page.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadOwnerPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/earningsReport.fxml"));
            Parent ownerPageRoot = loader.load();
    
            Stage stage = (Stage) signInButton.getScene().getWindow();
            stage.setScene(new Scene(ownerPageRoot));
            stage.setTitle("Earnings Report Page");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Unable to load the earnings report page.");
        }
    }
}

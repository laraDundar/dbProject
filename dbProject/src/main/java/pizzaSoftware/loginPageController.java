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
import java.io.IOException;

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

    public loginPageController() {
        loginManager = new LoginManager();
    }

    @FXML
    public void signInAction(ActionEvent event) {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Login Error", "Please fill in both username and password.");
            return;
        }

        boolean success = loginManager.login(username, password);

        if (success) {
            showAlert("Login Success", "Welcome, " + username + "!");
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
}

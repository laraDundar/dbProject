package pizzaSoftware;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import java.time.LocalDate;

public class signUpController {

    @FXML
    private TextField newAccountUsernameTextField;
    
    @FXML
    private TextField newAccountPasswordTextField;

    @FXML
    private TextField newAccountEmailTextField;
    
    @FXML
    private TextField newAccountNameTextField;

    @FXML
    private DatePicker newAccountBirthSelector;

    @FXML
    private TextField newAccountPhoneTextField;

    @FXML
    private ComboBox<String> newAccountGenderComboBox;

    @FXML
    private TextField newAccountAddressTextField;

    @FXML
    private TextField newAccountZipTextField;

    @FXML
    private Button signUpButton;

    private LoginManager loginManager;

    public signUpController() {
        this.loginManager = new LoginManager();
    }

    @FXML
    public void initialize() {
        newAccountGenderComboBox.getItems().addAll("Male", "Female");
    }

    @FXML
    private void signUpAction(ActionEvent event) {
        // Get data from the fields
        String username = newAccountUsernameTextField.getText();
        String password = newAccountPasswordTextField.getText();
        String email = newAccountEmailTextField.getText();
        String name = newAccountNameTextField.getText();
        LocalDate birthdate = newAccountBirthSelector.getValue();
        String phoneNumber = newAccountPhoneTextField.getText();
        String gender = newAccountGenderComboBox.getValue();
        String address = newAccountAddressTextField.getText();
        String zipCode = newAccountZipTextField.getText();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || name.isEmpty() ||
            birthdate == null || phoneNumber.isEmpty() || gender == null || address.isEmpty() || zipCode.isEmpty()) {
            showAlert("Error", "All fields must be filled out.");
            return;
        }

        Customer newCustomer = new Customer(email, username, name,  gender, birthdate, phoneNumber, address, zipCode);

        try {
            loginManager.registerCustomer(newCustomer, password);
            showAlert("Registration Success", "Customer registered successfully!");
            clearFields();
        } catch (Exception e) {
            showAlert("Registration Failed", "Customer not registered. Please try again.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        newAccountUsernameTextField.clear();
        newAccountPasswordTextField.clear();
        newAccountEmailTextField.clear();
        newAccountNameTextField.clear();
        newAccountBirthSelector.setValue(null);
        newAccountPhoneTextField.clear();
        newAccountGenderComboBox.setValue(null);
        newAccountAddressTextField.clear();
        newAccountZipTextField.clear();
    }

}

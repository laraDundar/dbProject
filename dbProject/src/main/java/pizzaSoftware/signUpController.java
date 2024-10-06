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
    private TextField newAccountSurnameTextField;

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
        String firstName = newAccountNameTextField.getText();
        String lastName = newAccountSurnameTextField.getText();
        LocalDate birthdate = newAccountBirthSelector.getValue();
        String phoneNumber = newAccountPhoneTextField.getText();
        String gender = newAccountGenderComboBox.getValue();
        String address = newAccountAddressTextField.getText();
        String zipCode = newAccountZipTextField.getText();

        Customer newCustomer = new Customer(email, username, firstName, lastName, gender, birthdate, phoneNumber, address, zipCode);

        loginManager.registerCustomer(newCustomer, password);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registration Success!");
        alert.setHeaderText(null);
        alert.setContentText("You are registered successfully!");
        alert.showAndWait();
    }

}

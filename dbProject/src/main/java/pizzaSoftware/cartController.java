package pizzaSoftware;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;

public class cartController {
    @FXML
    private Label yourOrderLabel;

    @FXML
    private Label discountsLabel;

    @FXML
    private TableView<?> checkoutCartTable;

    @FXML
    private Button proceedCheckoutButton;

    @FXML
    private RadioButton birthdayDiscountRButton;

    @FXML
    private Button backToMenuButton;

    @FXML
    private Label yourCartLabel;

    @FXML
    private RadioButton tenPizzasDiscountRButton;

    @FXML
    void proceedCheckoutAction(ActionEvent event) {

    }

    @FXML
    void backToMenuAction(ActionEvent event) {

    }
}

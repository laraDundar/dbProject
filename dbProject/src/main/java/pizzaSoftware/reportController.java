package pizzaSoftware;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class reportController {

    @FXML
    private TableView<?> reportTable;

    @FXML
    private Label filtersLabel;

    @FXML
    private ComboBox<String> ageComboBox;

    @FXML
    private Label reportTitleLabel;

    @FXML
    private ComboBox<?> regionComboBox;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    void regionAction(ActionEvent event) {

    }

    @FXML
    void genderAction(ActionEvent event) {

    }

    @FXML
    void ageAction(ActionEvent event) {

    }

    @FXML
    public void initialize() {
        genderComboBox.getItems().addAll("Male", "Female");
        ageComboBox.getItems().addAll("18-", "18-30", "30-40", "40-50", "50-60", "60+");
    }
    
}

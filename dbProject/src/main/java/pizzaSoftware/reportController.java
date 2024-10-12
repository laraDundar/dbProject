package pizzaSoftware;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class reportController {

    @FXML
    private TableView<annualReport> reportTable;

    @FXML
    private Label filtersLabel;

    @FXML
    private ComboBox<String> ageComboBox;

    @FXML
    private Label reportTitleLabel;

    @FXML
    private ComboBox<String> regionComboBox;

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
        regionComboBox.getItems().addAll("1000AB", "1001AC");

        initializeTable();
        filterReports();
    }

    private void initializeTable() {
        TableColumn<annualReport, Integer> idColumn = new TableColumn<>("Order ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        TableColumn<annualReport, Double> amountColumn = new TableColumn<>("Total Amount");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));

        TableColumn<annualReport, String> genderColumn = new TableColumn<>("Gender");
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        TableColumn<annualReport, String> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<annualReport, String> regionColumn = new TableColumn<>("Region");
        regionColumn.setCellValueFactory(new PropertyValueFactory<>("region"));

        reportTable.getColumns().addAll(idColumn, amountColumn, genderColumn, ageColumn, regionColumn);
    }

    private void filterReports() {
        String selectedGender = genderComboBox.getValue();
        String selectedAge = ageComboBox.getValue();
        String selectedRegion = regionComboBox.getValue();

        ObservableList<annualReport> reportData = FXCollections.observableArrayList();
        
        dbConnector db = new dbConnector();

        try (Connection conn = db.connect()) {
        String query = "SELECT o.order_id, o.price, c.gender, c.birthdate, c.zip_code " +
                   "FROM orders o JOIN customers c ON o.customer_id = c.customer_id " +
                   "WHERE (c.gender = ? OR ? IS NULL) " +
                   "AND (YEAR(CURDATE()) - YEAR(c.birthdate) BETWEEN ? AND ? OR ? IS NULL) " +
                   "AND (c.zip_code = ? OR ? IS NULL)";

            PreparedStatement statement = conn.prepareStatement(query);

            statement.setString(1, selectedGender);
            statement.setString(2, selectedGender);

            setAgeParameters(statement, selectedAge);

            statement.setString(6, selectedRegion);
            statement.setString(7, selectedRegion);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                reportData.add(new annualReport(rs.getInt("order_id"), 
                                               rs.getDouble("price"), 
                                               rs.getString("gender"), 
                                               rs.getString("birthdate"), 
                                               rs.getString("zip_code")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        reportTable.setItems(reportData);
    }

    private void setAgeParameters(PreparedStatement statement, String selectedAge) throws SQLException {
        switch (selectedAge) {
            case "18-":
                statement.setInt(3, 0);
                statement.setInt(4, 18);
                break;
            case "18-30":
                statement.setInt(3, 18);
                statement.setInt(4, 30);
                break;
            case "30-40":
                statement.setInt(3, 30);
                statement.setInt(4, 40);
                break;

            default:
                statement.setNull(3, java.sql.Types.INTEGER);
                statement.setNull(4, java.sql.Types.INTEGER);
                break;
        }
        statement.setString(5, selectedAge);
    }

    
}

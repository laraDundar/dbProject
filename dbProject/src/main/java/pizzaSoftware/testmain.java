package pizzaSoftware;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class testmain extends Application{
        @Override
    public void start(Stage primaryStage) {
        try {
            // Load the loginPage.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginMainPage.fxml"));
            Parent root = loader.load();

            // Set the scene with the login page and configure the stage
            primaryStage.setTitle("Pizza Ordering System - Login");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}

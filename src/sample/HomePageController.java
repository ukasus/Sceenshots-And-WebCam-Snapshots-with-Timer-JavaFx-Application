package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageController {
    @FXML
    Button startTrackingButton;
    @FXML
    Button viewActivityButton;


    public void startTracking(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("TrackingPage.fxml"));
        stage.setScene(new Scene(root, 700, 275));
        stage.show();
    }

    public void viewActivity(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("ViewActivityPage.fxml"));
        stage.setScene(new Scene(root, 300, 275));
        stage.show();
    }
}

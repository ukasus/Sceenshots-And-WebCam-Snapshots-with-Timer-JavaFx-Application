package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ViewActivityController implements Initializable {
    @FXML
    DatePicker datePicker;
    @FXML
    Button viewRecords;
    @FXML
    Label label;

    DateTimeFormatter dtfForDirectory = DateTimeFormatter.ofPattern("dd-MM-yyyy");



    public void checkFolder(javafx.event.ActionEvent event) throws IOException {
        File file=new File(dtfForDirectory.format(datePicker.getValue()));
        if(file.exists())
        {
            DataRecordsController.file=file;

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            DataRecordsController.stage=stage;
            Parent root = FXMLLoader.load(getClass().getResource("DataRecordsPage.fxml"));
            stage.setScene(new Scene(root, 700, 275));
            stage.show();
        }
        else
        {
            label.setText("No Data is available for this date.Try another one!");

        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        datePicker.setValue(LocalDate.now());
    }

    public void backToHomePage(javafx.event.ActionEvent event) throws  IOException{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage.setScene(new Scene(root, 400, 275));
        stage.show();
    }
}

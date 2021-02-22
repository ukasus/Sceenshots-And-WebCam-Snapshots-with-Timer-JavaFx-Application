package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthenticationPageController {
    @FXML
    Button login;

    @FXML
   public TextField username;
    @FXML
    public TextField password;

    public void print(ActionEvent event) throws IOException {
        String user=username.getText();
        String passKey=password.getText();
//        if(user.equals("Ujjawal") && passKey.equals("12345"))
//        {
            System.out.println("Inside If");
            Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
            stage.setScene(new Scene(root, 700, 275));
            stage.show();

//        }
//        else
//            System.out.println("Invalid login");
       //System.out.println(username.getText()+" "+password.getText());
    }
}

package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class TrackingPageController implements  Initializable{
    @FXML
    Label secLabel;
    @FXML
    Label minLabel;
    @FXML
    Label hrsLabel;
    @FXML
    Button pauseButton;
    @FXML
    Button stopButton;
    static int timeToSS=0;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm");
    LocalDateTime now;
   //System.out.println(dtf.format(now));


    static long i=0;
   Timer t;
   static boolean isPause=false,start=false;


   public void pauseCount(){
       if(start)
       {
           startCount();
           pauseButton.setText("Pause");
           start=false;
       }

       else if(!isPause)
       {
           pauseButton.setText("Resume");
           try {
               t.cancel();
               isPause=true;
           }
           catch (Exception e)
           {

           }



       }
       else
       {
           pauseButton.setText("Pause");
           startCount();
           isPause=false;
       }

   }
   public void stopCount(){

       t.cancel();
       i=0;
       secLabel.setText("00");
       minLabel.setText("00");
       hrsLabel.setText("00");
       isPause=false;
       start=true;
       pauseButton.setText("Start Again");

   }













    public  void startCount()
    {


        t=new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(()->{

                    if(timeToSS%300==0 && timeToSS!=0)
                    {
                      takeScreenShots();
                    }
                    secLabel.setText(String.valueOf(i%60));
                    minLabel.setText(String.valueOf(i/60));
                    hrsLabel.setText(String.valueOf(i/3600));

                    i+=1;
                    timeToSS+=1;

                });

            }
        },0,1000);

    }
 public void takeScreenShots()
 {
     try {
         Robot robot=new Robot();
         String format="jpg";
         now=LocalDateTime.now();

         String fileName=dtf.format(now)+".jpg";
         Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
         BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
         ImageIO.write(screenFullImage, format, new File(fileName));
         timeToSS=0;

     } catch (Exception e) {
         e.printStackTrace();
     }
 }

 public void takeSnapShotThroughWebcam()
 {
     WritableImage writableImage=null;

 }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pauseButton.setText("Pause");

        startCount();
    }
}

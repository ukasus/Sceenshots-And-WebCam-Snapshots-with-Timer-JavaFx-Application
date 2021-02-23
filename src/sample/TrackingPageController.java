package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class TrackingPageController implements Initializable {
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


    static int timeToSS = 0;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
    DateTimeFormatter dtfForDirectory = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    LocalDateTime now;
    File directory;


    static long totalSeconds = 0;
    Timer t;
    static boolean isPause = false, start = false;


    public void pauseCount() {
        if (start) {
            startCount();
            pauseButton.setText("Pause");
            start = false;
        } else if (!isPause) {
            pauseButton.setText("Resume");
            try {
                t.cancel();
                isPause = true;
            } catch (Exception e) {

            }


        } else {
            pauseButton.setText("Pause");
            startCount();
            isPause = false;
        }

    }

    public void stopCount() {

        t.cancel();
        totalSeconds = 0;
        secLabel.setText("00");
        minLabel.setText("00");
        hrsLabel.setText("00");
        isPause = false;
        start = true;
        pauseButton.setText("Start Again");
        new Thread(new Runnable() {
            @Override
            public void run() {
                takeSnapShotThroughWebcam();
            }
        }).start();

    }


    public void startCount() {


        t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {

                    if (timeToSS % 300 == 0 && timeToSS != 0) {
                        takeScreenShots();

                    }
                    secLabel.setText(String.valueOf(totalSeconds % 60));
                    minLabel.setText(String.valueOf(totalSeconds / 60));
                    hrsLabel.setText(String.valueOf(totalSeconds / 3600));

                    totalSeconds += 1;
                    timeToSS += 1;

                });

            }
        }, 0, 1000);

    }

    public void takeScreenShots() {
        try {
            Robot robot = new Robot();
            String format = "jpg";
            now = LocalDateTime.now();


            String fileName = directory.getAbsolutePath() + "/" + dtf.format(now) + ".jpg";

            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
            ImageIO.write(screenFullImage, format, new File(fileName));
            timeToSS = 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void takeSnapShotThroughWebcam() {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        VideoCapture capture = new VideoCapture(0);
        Mat matrix = new Mat();
        capture.read(matrix);

        if (capture.isOpened()) {
            if (capture.read(matrix)) {
                BufferedImage image = new BufferedImage(matrix.width(), matrix.height(), BufferedImage.TYPE_3BYTE_BGR);
                WritableRaster raster = image.getRaster();
                DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
                byte[] data = dataBuffer.getData();
                matrix.get(0, 0, data);

            }
        }
        now = LocalDateTime.now();
        directory = new File(dtfForDirectory.format(now));
        directory.mkdir();

        String fileName = directory.getAbsolutePath() + "/" + dtf.format(now) + "webcam.jpg";

        // Instantiating the imgcodecs class
        Imgcodecs imageCodecs = new Imgcodecs();

        // Saving it again
        imageCodecs.imwrite(fileName, matrix);


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pauseButton.setText("Pause");

        startCount();
        new Thread(new Runnable() {
            @Override
            public void run() {

                takeSnapShotThroughWebcam();
            }
        }).start();
    }
}

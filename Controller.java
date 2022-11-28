package sample;

import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class Controller implements Initializable{

    private String filePath;
    @FXML
    private MediaView mediaview;

    @FXML
    private Button playBtn;

    @FXML
    private Slider slidHere;

    @FXML
    private Slider seekSlider;

    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    private void pauseVideo(javafx.event.ActionEvent event){
        mediaPlayer.pause();
    }

    @FXML
    private void playVideo(javafx.event.ActionEvent event){
        mediaPlayer.play();
        mediaPlayer.setRate(1);
    }

    @FXML
    private void stopVideo(javafx.event.ActionEvent event){
        mediaPlayer.stop();
    }

    @FXML
    private void fastVideo(javafx.event.ActionEvent event){
       double d = mediaPlayer.getCurrentTime().toSeconds();
       d = d + 5;
       mediaPlayer.seek(new Duration(d*1000));
    }

    @FXML
    private void slowVideo(javafx.event.ActionEvent event){

        double d = mediaPlayer.getCurrentTime().toSeconds();
        d = d - 5;
        mediaPlayer.seek(new Duration(d*1000));
    }

    @FXML
    private void slowerVideo(javafx.event.ActionEvent event){
        mediaPlayer.setRate(.75);
    }


    @FXML
    private void fasterVideo(javafx.event.ActionEvent event){
        mediaPlayer.setRate(1.5);
    }

    @FXML
    private void exitVideo(javafx.event.ActionEvent event){
        System.exit(0);
    }

    public void handleButtonAction(javafx.event.ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a File (*.mp4)","*.mp4");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        filePath = file.toURI().toString();

        if(filePath != null){
            Media media = new Media(filePath);
            mediaPlayer = new MediaPlayer(media);
            mediaview.setMediaPlayer(mediaPlayer);
//            DoubleProperty width = mediaview.fitWidthProperty();
//            DoubleProperty height = mediaview.fitHeightProperty();
//            width.bind(Bindings.selectDouble(mediaview.sceneProperty(),"width"));
//            height.bind(Bindings.selectDouble(mediaview.sceneProperty(),"height"));
            slidHere.setValue(mediaPlayer.getVolume()*100);
            slidHere.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mediaPlayer.setVolume(slidHere.getValue()/100);
                }
            });

            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                    seekSlider.setValue(t1.toSeconds());
                }
            });

            seekSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    mediaPlayer.seek(Duration.seconds(seekSlider.getValue()));
                }
            });

            mediaPlayer.play();


        }
    }
}

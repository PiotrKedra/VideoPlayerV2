package main;

import javafx.event.ActionEvent;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.event.EventHandler;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    private boolean isPlaying = true;

    @FXML private MediaView mediaView;
    private MediaPlayer mediaPlayer;
    private Media media;

    @FXML private Button playPause;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String path =  new File("C:/Users/piotr/Desktop/tgt.mp4").getAbsolutePath();
        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);
        DoubleProperty width=mediaView.fitWidthProperty();
        DoubleProperty height=mediaView.fitHeightProperty();
        width.bind(Bindings.selectDouble(mediaView.sceneProperty(),"width"));
        height.bind(Bindings.selectDouble(mediaView.sceneProperty(),"height"));


        //need to change for lambdas but how xd
        /*playPause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                playOrPause();
            }
        });*/
        //this is lambda ;o, shorter but...
        playPause.setOnAction((event) -> playOrPause());

    }

    private void playOrPause(){
        if (isPlaying) {
            isPlaying = !isPlaying;
            mediaPlayer.pause();
        } else {
            isPlaying = !isPlaying;
            mediaPlayer.play();
        }
    }



}

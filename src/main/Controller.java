package main;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    private boolean isPlaying = true;

    @FXML private MediaView mediaView;
    private MediaPlayer mediaPlayer;
    private Media media;

    @FXML private HBox bottomHBox;
    @FXML private GridPane bottomMenu;
    @FXML private Button playPause;
    @FXML private Button scrollBack;
    @FXML private Button scrollForward;
    @FXML private Button fastScrollBack;
    @FXML private Button fastScrollForward;
    @FXML private Button fullScreen;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String path =  new File("C:/Users/piotr/Desktop/tgt.mp4").getAbsolutePath();
        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);
       /* DoubleProperty width=mediaView.fitWidthProperty();
        DoubleProperty height=mediaView.fitHeightProperty();
        width.bind(Bindings.selectDouble(mediaView.sceneProperty(),"width"));
        height.bind(Bindings.selectDouble(mediaView.sceneProperty(),"height"));*/


        /*playPause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                playOrPause();
            }
        });*/
        //this is lambda ;o, shorter but...
        playPause.setOnAction((event) -> playOrPause());
        scrollBack.setOnAction(event -> scroll(-5000));
        scrollForward.setOnAction(event -> scroll(5000));
        fastScrollBack.setOnAction(event -> scroll(-20000));
        fastScrollForward.setOnAction(event -> scroll(20000));
        fullScreen.setOnAction(event -> setFullScreen());

        bottomHBox.setAlignment(Pos.CENTER);
       // bottomBar.getStylesheets().add("style/BottomBar.css");
       // playPause.getStylesheets().add("style/BottomBar.css");

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

    private void scroll(int millis){
        if(mediaPlayer!=null) {
            Duration currentTime = mediaPlayer.getCurrentTime();
            currentTime = currentTime.add(new Duration(millis));
            mediaPlayer.seek(currentTime);
        }
    }

    private void setFullScreen(){
        Stage stage = Main.getStage();
        stage.setFullScreen(!stage.isFullScreen());
        if(Main.getStage().isFullScreen()){
            //menu have to disapear
        }else {
            //menu gave to be back
        }

    }


}

package main;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
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
    @FXML private GridPane leftGridPane;
    @FXML private HBox leftHBox;
    @FXML private ProgressBar progressBar;
    @FXML private Text currentDuration;
    @FXML private Text duration;

    @FXML private ImageView playPause;
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

        //playPause.setOnAction((event) -> playOrPause());
        scrollBack.setOnAction(event -> scroll(-5000));
        scrollForward.setOnAction(event -> scroll(5000));
        fastScrollBack.setOnAction(event -> scroll(-20000));
        fastScrollForward.setOnAction(event -> scroll(20000));
        fullScreen.setOnAction(event -> setFullScreen());

        final Image image = new Image(getClass().getResourceAsStream("/pngs/play.png"));
        //playPause.setGraphic(new ImageView(image));

        playPause.setImage(image);
       // bottomBar.getStylesheets().add("style/BottomBar.css");
       // playPause.getStylesheets().add("style/BottomBar.css");




        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                currentDuration.setText(Double.toString(newValue.toSeconds()));
                progressBar.setProgress(newValue.toSeconds()/Double.parseDouble(duration.getText()));

            }
        });


        progressBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                progressBar.setProgress(event.getX()/(progressBar.getWidth())+0.01); //manulay add 1% ??xd
                System.out.println("changed progres %: " + progressBar.getProgress());
                double newDurationOfMedia = progressBar.getProgress()*media.getDuration().toMillis();
                mediaPlayer.seek(new Duration(newDurationOfMedia));
            }
        });


        //its oke, but need to get an explenation of why i have to add 1% so it works
        progressBar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(progressBar.getLayoutX());
                System.out.println(event.getX());
                progressBar.setProgress(event.getX()/(progressBar.getWidth())+0.01); //manulay add 1% ??xd
                System.out.println("changed progres %: " + progressBar.getProgress());
                double newDurationOfMedia = progressBar.getProgress()*media.getDuration().toMillis();
                mediaPlayer.seek(new Duration(newDurationOfMedia));
            }
        });
        progressBar.onDragDetectedProperty();
        mediaPlayer.setOnReady(()->{
            currentDuration.setText("0");
            System.out.println(currentDuration.getText());
            System.out.println(media.getDuration());
            duration.setText(Double.toString(media.getDuration().toSeconds()));
        });


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

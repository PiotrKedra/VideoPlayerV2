package main;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.helpFunction.MouseStatus;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class Controller implements Initializable{

    private MouseStatus mouseStatus = new MouseStatus();

    private boolean isPlaying = true;
    private boolean isFullScreen = false;
    private boolean isSoundOn = true;
    private double volumeBeforeChange = 1.0;

    @FXML private MediaView mediaView;
    private MediaPlayer mediaPlayer;
    private Media media;

    @FXML private Slider soundSlider;
    @FXML private GridPane bottomMenu;
    @FXML private BorderPane mainPane;
    @FXML private ProgressBar progressBar;
    @FXML private Text currentDuration;
    @FXML private Text duration;

    @FXML private ImageView playPause;
    @FXML private ImageView Rewind;
    @FXML private ImageView Forward;
    @FXML private ImageView fastRewind;
    @FXML private ImageView fastForward;
    @FXML private ImageView fullScreen;
    @FXML private ImageView subtitles;
    @FXML private ImageView soundOn;

    @FXML private ImageView addMovieButton;
    private String moviePath;
    final Popup popup = new Popup();


    //images, icons
    private final Image playImage = new Image("/pngs/play.png");
    private final Image play2Image = new Image("/pngs/play2.png");
    private final Image pauseImage = new Image("/pngs/pause.png");
    private final Image pause2Image = new Image("/pngs/pause2.png");
    private final Image fastForward2Image = new Image("/pngs/fastForward2.png");
    private final Image fastForwardImage = new Image("/pngs/fastForward.png");
    private final Image fastRewind2Image = new Image("/pngs/fastRewind2.png");
    private final Image fastRewindImage = new Image("/pngs/fastRewind.png");
    private final Image subtitles2Image = new Image("/pngs/subtitles2.png");
    private final Image subtitlesImage = new Image("/pngs/subtitles.png");
    private final Image fullScreen2Image = new Image("/pngs/fullScreen2.png");
    private final Image fullScreenImage = new Image("/pngs/fullScreen.png");
    private final Image soundsOn2Image = new Image("/pngs/speakerOn2.png");
    private final Image soundsOnImage = new Image("/pngs/speakerOn.png");
    private final Image soundsOff2Image = new Image("/pngs/speakerOff2.png");
    private final Image soundsOffImage = new Image("/pngs/speakerOff.png");
    private final Image normalScreen2Image = new Image("/pngs/normalScreen2.png");
    private final Image normalScreenImage = new Image("/pngs/normalScreen.png");
    private final Image addMovie2Image = new Image("/pngs/addMovie2.png");
    private final Image addMovieImage = new Image("pngs/addMovie.png");
    private final Image forwardImage = new Image("pngs/forward.png");
    private final Image forward2Image = new Image("pngs/forward2.png");
    private final Image rewindImage = new Image("pngs/rewind.png");
    private final Image rewind2Image = new Image("pngs/rewind2.png");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListinerForMovingMouse();
        /*String path =  new File("C:/Users/piotr/Desktop/tgt.mp4").getAbsolutePath();
        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);*/

        //binding mediaView to scene width and height
        /*DoubleProperty width=mediaView.fitWidthProperty();
        DoubleProperty height=mediaView.fitHeightProperty();
        width.bind(Bindings.selectDouble(mediaView.sceneProperty(),"width"));
        height.bind(Bindings.selectDouble(mediaView.sceneProperty(),"height"));*/

        mainPane.addEventHandler(KeyEvent.KEY_PRESSED,keyEvent->{
                        if (keyEvent.getCode() == KeyCode.ESCAPE) {
                            if(isFullScreen){
                                isFullScreen = false;
                                fullScreen.setImage(fullScreenImage);
                            }
                        }
                    });

        fastForward.setOnMouseClicked(event -> scroll(20000));
        fastRewind.setOnMouseClicked(event -> scroll(-20000));
        Forward.setOnMouseClicked(event -> scroll(3000));
        Rewind.setOnMouseClicked(event -> scroll(-3000));

        progressBar.setOnMouseDragged(event -> {
            progressBar.setProgress(event.getX()/(progressBar.getWidth())+0.01);
            double newDurationOfMedia = progressBar.getProgress()*media.getDuration().toMillis();
            mediaPlayer.seek(new Duration(newDurationOfMedia));
        });

        //its oke, but need to get an explenation of why i have to add 1% so it works
        progressBar.setOnMouseClicked(event->{
                progressBar.setProgress(event.getX()/(progressBar.getWidth())+0.01); //manulay add 1% ??xd
                double newDurationOfMedia = progressBar.getProgress()*media.getDuration().toMillis();
                mediaPlayer.seek(new Duration(newDurationOfMedia));
            });

        soundSlider.valueProperty().addListener((observable, oldValue,newValue)->{
                mediaPlayer.setVolume(newValue.doubleValue()/100);
                if(newValue.doubleValue()==0) {
                    volumeBeforeChange = oldValue.doubleValue()/100;
                    isSoundOn=!isSoundOn;
                    soundOn.setImage(soundsOffImage);
                }
                if(oldValue.doubleValue()==0) {
                    isSoundOn=!isSoundOn;
                    soundOn.setImage(soundsOnImage);
                    if(mediaPlayer.getVolume()<0.3) soundSlider.setValue(30);
                }
            });


    }

    public void playOrPause(){
        if (isPlaying) {
            isPlaying = false;
            mediaPlayer.pause();
            playPause.setImage(play2Image);
        } else {
            isPlaying = true;
            mediaPlayer.play();
            playPause.setImage(pause2Image);
        }
    }

    private void scroll(int millis){
        if(mediaPlayer!=null) {
            Duration currentTime = mediaPlayer.getCurrentTime();
            currentTime = currentTime.add(new Duration(millis));
            mediaPlayer.seek(currentTime);
        }
    }

    public void setFullScreen(){
        Stage stage = Main.getStage();
        stage.setFullScreen(!stage.isFullScreen());
        isFullScreen = !isFullScreen;

    }

    //change secuds to HH:MM:SS, exm: 310 -> HH:MM:SS
    private String secundsToHHMMSS(double sec){
        int intSec = (int) sec;
        Integer secunds = intSec%60;
        Integer minutes = (intSec/60)%60;
        Integer hours = ((intSec/60)/60)%60;
        String stringSecunds;
        String stringMinutes;
        String stringHours;
        if(secunds<10){
            stringSecunds = 0 + secunds.toString(); // 01, 02..
        }else stringSecunds = secunds.toString(); // 12,42 ...
        if(minutes<10){
            stringMinutes = 0 + minutes.toString();
        }else stringMinutes = minutes.toString();
        if(hours<10){
            stringHours = 0 + hours.toString();
        }else stringHours = hours.toString();

        return stringHours+':'+stringMinutes+':'+stringSecunds;
    }

    //HH:MM:SS to secunds
    //example: 00:05:10 -> 310
    private int HHMMSStoSecunds(String HHMMSS){
        Pattern pattern = Pattern.compile(":");
        int secunds = 0;
        String[] time = pattern.split(HHMMSS);
        secunds += Integer.parseInt(time[0])*3600;  //hours = 3600 secunds
        secunds += Integer.parseInt(time[1])*60; //minutes 60 secunds
        secunds += Integer.parseInt(time[2]); //secunds
        return secunds;
    }

    //testing stuff
    public static void main(String [] a){
        /*String h=secundsToHHMMSS(3996.2332123);
        System.out.println(h);
        System.out.println(HHMMSStoSecunds(h));*/
    }


    public void mute(){
        if(!isSoundOn) {
            soundSlider.setValue(volumeBeforeChange*100);
        }
        else {
            soundSlider.setValue(0);
        }
    }

    // ###### changing image while mouse on it, nothing interested
    public void changeImagePlayOnMouseEntered() {
        if(isPlaying) playPause.setImage(pause2Image);
        else playPause.setImage(play2Image);
    }

    public void changeImagePlayOnMouseExited() {
        if(isPlaying) playPause.setImage(pauseImage);
        else playPause.setImage(playImage);
    }

    public void changeImageFastForwardOnMouseEntered() {
        fastForward.setImage(fastForward2Image);
    }

    public void changeImageFastForwardOnMouseExited() {
        fastForward.setImage(fastForwardImage);
    }

    public void changeImageFastRewindOnMouseEntered() {
        fastRewind.setImage(fastRewind2Image);
    }

    public void changeImageFastRewindOnMouseExited() {
        fastRewind.setImage(fastRewindImage);
    }

    public void changeImageSubtitlesOnMouseEntered() {
        subtitles.setImage(subtitles2Image);
    }

    public void changeImageSubtitlesOnMouseExited() {
        subtitles.setImage(subtitlesImage);
    }

    public void changeImageAddMovieOnMouseEntered() {
        addMovieButton.setImage(addMovie2Image);
    }

    public void changeImageAddMovieOnMouseExited(){
        addMovieButton.setImage(addMovieImage);
    }

    public void changeImageForwardOnMouseEntered() {
        Forward.setImage(forward2Image);
    }

    public void changeImageForwardOnMouseExited() {
        Forward.setImage(forwardImage);
    }

    public void changeImageRewindOnMouseEntered() {
        Rewind.setImage(rewind2Image);
    }

    public void changeImageRewindOnMouseExited() {
        Rewind.setImage(rewindImage);
    }


    public void changeImageFullScreenOnMouseEntered() {
        if(!isFullScreen) fullScreen.setImage(fullScreen2Image);
        else fullScreen.setImage(normalScreen2Image);
    }

    public void changeImageFullScreenOnMouseExited() {
        if(!isFullScreen) fullScreen.setImage(fullScreenImage);
        else fullScreen.setImage(normalScreenImage);
    }

    public void changeImageSoundsOnOnMouseEntered() {
        if(isSoundOn) soundOn.setImage(soundsOn2Image);
        else soundOn.setImage(soundsOff2Image);
    }

    public void changeImageSoundsOnOnMouseExited() {
        if(isSoundOn) soundOn.setImage(soundsOnImage);
        else soundOn.setImage(soundsOffImage);
    }

    public void changeColorOfProgressBarOnMouseEntered() {
        System.out.println("hohohohohho");
        //progressBar.setStyle(".progress-Bar .bar -fx-background-color:orangered;");
        progressBar.getStylesheets().clear();
        progressBar.getStylesheets().setAll("/style/progressBar2.css");

    }

    public void changeColorOfProgressBarOnMouseExited() {
        progressBar.getStylesheets().clear();
        progressBar.getStylesheets().setAll("/style/progresBar.css");
    }

    public void showMenuWhenFullScreen() {
        if(isFullScreen){
            mainPane.setBottom(bottomMenu);
        }
    }

    //handle disapiring of menu when no active
    private void addListinerForMovingMouse() {

        mainPane.addEventFilter(MouseEvent.ANY, e -> {

            if(isFullScreen) mainPane.setBottom(bottomMenu);

            mouseStatus.setX(e.getX());
            mouseStatus.setY(e.getY());
            mouseStatus.setPrimaryButtonDown(e.isPrimaryButtonDown());
            mouseStatus.setSecondaryButtonDown(e.isSecondaryButtonDown());

        });

        AnimationTimer loop = new AnimationTimer() {

            long deltaNs = 900_000_000;

            double currX;
            double currY;
            long currNs;

            double prevX;
            double prevY;
            long prevNs;

            @Override
            public void handle(long now) {

                currX = mouseStatus.getX();
                currY = mouseStatus.getY();
                currNs = now;

                if( currNs - prevNs > deltaNs) {

                    if( prevX == currX && prevY == currY) {
                        if(isFullScreen) mainPane.setBottom(null);
                    }

                    prevX = currX;
                    prevY = currY;
                    prevNs = currNs;
                }

            }
        };
        loop.start();

    }


    private void restartMediaPlayer(String moviePath){
        try {
            System.out.println(moviePath);
            //this geAbsolutPath is rly important, without it it doesnt wokr?????
            String path = new File(moviePath).getAbsolutePath();

            media = new Media(new File(path).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.setAutoPlay(true);
        }catch (Exception e){
            System.out.println("cus zlego patha dales");
        }
        try { mediaPlayer.currentTimeProperty().addListener(
                    (ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) -> {
                        currentDuration.setText(secundsToHHMMSS(newValue.toSeconds()));
                        progressBar.setProgress(newValue.toSeconds() / HHMMSStoSecunds(duration.getText()));
                    });

            mediaPlayer.setOnReady(() -> {
                currentDuration.setText("00:00:00");
                System.out.println(currentDuration.getText());
                System.out.println(media.getDuration());
                duration.setText(secundsToHHMMSS(media.getDuration().toSeconds()));
            });
        }catch (Exception e){
            System.out.println("cus zlego sie stalo");
        }




            DoubleProperty width = mediaView.fitWidthProperty();
            DoubleProperty height = mediaView.fitHeightProperty();
            width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
            height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));

            mainPane.setCenter(mediaView);

    }
    public void addMovie() {
        popup.display();
    }

    private class Popup {
        private TextField pathField = new TextField("C:/Users/piotr/Desktop/tgt.mp4");

        private void display()
        {
            Stage popupwindow=new Stage();
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.setTitle("Give movie path");
            popupwindow.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    System.out.println("dsddssdssdds");
                    System.out.println(getPath());
                    restartMediaPlayer(getPath());
                    popupwindow.close();
                }
            });
            VBox layout= new VBox(10);
            pathField = new TextField("C:/Users/piotr/Desktop/tgt.mp4");
            layout.getChildren().addAll(pathField);
            layout.setAlignment(Pos.CENTER);
            Scene scene1= new Scene(layout, 300, 250);
            popupwindow.setScene(scene1);
            popupwindow.showAndWait();
        }

        private String getPath() {
            return pathField.getText();
        }
    }
}

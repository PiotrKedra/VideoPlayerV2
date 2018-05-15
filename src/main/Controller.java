package main;

import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
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
    @FXML private HBox bottomHBox;
    @FXML private GridPane bottomMenu;
    @FXML private BorderPane mainPane;
    @FXML private GridPane leftGridPane;
    @FXML private HBox leftHBox;
    @FXML private ProgressBar progressBar;
    @FXML private Text currentDuration;
    @FXML private Text duration;

    @FXML private ImageView playPause;
    @FXML private ImageView scrollBack;
    @FXML private ImageView scrollForward;
    @FXML private ImageView fastRewind;
    @FXML private ImageView fastForward;
    @FXML private ImageView fullScreen;
    @FXML private ImageView subtitles;
    @FXML private ImageView soundOn;


    //images, icons
    private Image playImage = new Image("/pngs/play.png");
    private Image play2Image = new Image("/pngs/play2.png");
    private Image pauseImage = new Image("/pngs/pause.png");
    private Image pause2Image = new Image("/pngs/pause2.png");
    private Image fastForward2Image = new Image("/pngs/fastForward2.png");
    private Image fastForwardImage = new Image("/pngs/fastForward.png");
    private Image fastRewind2Image = new Image("/pngs/fastRewind2.png");
    private Image fastRewindImage = new Image("/pngs/fastRewind.png");
    private Image subtitles2Image = new Image("/pngs/subtitles2.png");
    private Image subtitlesImage = new Image("/pngs/subtitles.png");
    private Image fullScreen2Image = new Image("/pngs/fullScreen2.png");
    private Image fullScreenImage = new Image("/pngs/fullScreen.png");
    private Image soundsOn2Image = new Image("/pngs/speakerOn2.png");
    private Image soundsOnImage = new Image("/pngs/speakerOn.png");
    private Image soundsOff2Image = new Image("/pngs/speakerOff2.png");
    private Image soundsOffImage = new Image("/pngs/speakerOff.png");
    private Image normalScreen2Image = new Image("/pngs/normalScreen2.png");
    private Image normalScreenImage = new Image("/pngs/normalScreen.png");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListinerForMovingMouse();
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
/*        scrollBack.setOnAction(event -> scroll(-5000));
        scrollForward.setOnAction(event -> scroll(5000));
        fastScrollBack.setOnAction(event -> scroll(-20000));*/

        //playPause.setGraphic(new ImageView(image));

       // bottomBar.getStylesheets().add("style/BottomBar.css");
       // playPause.getStylesheets().add("style/BottomBar.css");


        //playPause.setOnMouseClicked(event -> playOrPause());

        fastForward.setOnMouseClicked(event -> scroll(20000));
        fastRewind.setOnMouseClicked(event -> scroll(-20000));

        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                currentDuration.setText(secundsToHHMMSS(newValue.toSeconds()));
                progressBar.setProgress(newValue.toSeconds()/HHMMSStoSecunds(duration.getText()));
            }
        });


        progressBar.setOnMouseDragged(event -> {
            progressBar.setProgress(event.getX()/(progressBar.getWidth())+0.01);
            double newDurationOfMedia = progressBar.getProgress()*media.getDuration().toMillis();
            mediaPlayer.seek(new Duration(newDurationOfMedia));
        });

        //its oke, but need to get an explenation of why i have to add 1% so it works
        progressBar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                progressBar.setProgress(event.getX()/(progressBar.getWidth())+0.01); //manulay add 1% ??xd
                double newDurationOfMedia = progressBar.getProgress()*media.getDuration().toMillis();
                mediaPlayer.seek(new Duration(newDurationOfMedia));
            }
        });

        soundSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
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
            }
        });

        mediaPlayer.setOnReady(()->{
            currentDuration.setText("00:00:00");
            System.out.println(currentDuration.getText());
            System.out.println(media.getDuration());
            duration.setText(secundsToHHMMSS(media.getDuration().toSeconds()));
        });


    }

    public void playOrPause(){
        if (isPlaying) {
            isPlaying = !isPlaying;
            mediaPlayer.pause();
            playPause.setImage(play2Image);
        } else {
            isPlaying = !isPlaying;
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

        if(isFullScreen){
            fullScreen.setImage(normalScreen2Image);
            mainPane.setBottom(null);
            //menu have to disapear
        }else {
            //menu have to be back
            mainPane.setBottom(bottomMenu);
        }

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

    public void showMenuWhenFullScreen(MouseEvent mouseEvent) {
        if(isFullScreen){
            mainPane.setBottom(bottomMenu);
        }
    }

    private void addListinerForMovingMouse() {

        mainPane.addEventFilter(MouseEvent.ANY, e -> {

            System.out.println("ruszam");
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
                        System.out.println("nie ruszam xd");
                    }

                    prevX = currX;
                    prevY = currY;
                    prevNs = currNs;
                }

            }
        };
        loop.start();

    }
}

package main.helpFunction;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Controller;

public class Popup {
    private TextField pathField = new TextField("give path");

    public void display()
    {
        Stage popupwindow=new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Give movie path");
        popupwindow.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                //Controller.restartMediaPlayer(getPath());
                popupwindow.close();
            }
        });
        VBox layout= new VBox(10);
        TextField pathField = new TextField("give path");
        layout.getChildren().addAll(pathField);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 300, 250);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
    }

    public String getPath() {
        return pathField.getText();
    }
}

package main;
import javafx.application.Application;

import javafx.event.ActionEvent;

import javafx.event.EventHandler;

import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javafx.scene.shape.Circle;

import javafx.stage.*;


public class test extends Application {

    public static void main(String[] args) { launch(args); }



    @Override public void start(final Stage primaryStage) {
        primaryStage.setTitle("Popup Example");
        final Popup popup = new Popup();
        Button show = new Button("Show");
        show.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                popup.display();
            }
        });
        Button hide = new Button("Hide");
        hide.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
            }

        });



        HBox layout = new HBox(10);

        layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");

        layout.getChildren().addAll(show, hide);

        primaryStage.setScene(new Scene(layout));

        primaryStage.show();

    }

    public class Popup {


        public void display()
        {
            Stage popupwindow=new Stage();
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.setTitle("Give movie path");
            popupwindow.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    popupwindow.close();
                }
            });
            VBox layout= new VBox(10);
            //layout.getChildren().addAll(label1, button1);
            layout.setAlignment(Pos.CENTER);
            Scene scene1= new Scene(layout, 300, 250);
            popupwindow.setScene(scene1);
            popupwindow.showAndWait();
        }

    }

}

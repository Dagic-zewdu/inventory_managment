package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.net.Socket;

public class Main extends Application {
  @Override
    public void start(Stage primaryStage) throws Exception{
      Parent root=FXMLLoader.load(getClass().getResource("Main.fxml")); // scene builder loader
     primaryStage.setTitle("Final project");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

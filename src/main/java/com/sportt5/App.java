package com.sportt5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private static final int MAIN_WIDTH = 1280;
    private static final int MAIN_HEIGHT = 1024;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(App.class.getResource("/com.sportt5/view/view.fxml"));
        Scene scene = new Scene(root, MAIN_WIDTH, MAIN_HEIGHT);
        scene.getStylesheets().add(App.class.getResource("/com.sportt5/css/style.css").toExternalForm());

        stage.setTitle("Sport T5");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

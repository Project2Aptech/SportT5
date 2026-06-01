package com.sportt5;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class SongPlayerApp extends Application {
    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage primaryStage) {
        System.out.println(System.getProperty("java.library.path"));

        System.out.println(
                javafx.scene.media.Media.class
                        .getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
        );
        // 1. Point to your music file (relative or absolute path)
        String path = "src/main/resources/com.sportt5/songs/Anh_Quân_Idol_-_E_Là_Không_Thể.mp3";
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        // 2. Create control buttons
        Button playBtn = new Button("Play");
        Button pauseBtn = new Button("Pause");

        // 3. Assign button actions
        playBtn.setOnAction(e -> mediaPlayer.play());
        pauseBtn.setOnAction(e -> mediaPlayer.pause());

        // 4. Construct Layout and Scene
        HBox root = new HBox(10, playBtn, pauseBtn);
        Scene scene = new Scene(root, 200, 100);

        primaryStage.setTitle("JavaFX Music Player");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package com.sportt5;

import com.sportt5.model.Users;
import com.sportt5.service.AuthService;
import com.sportt5.session.TokenStorage;
import com.sportt5.session.UserSession;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private static final int MAIN_WIDTH = 1220;
    private static final int MAIN_HEIGHT = 810;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root;
        String token = TokenStorage.loadToken();
        boolean validToken = false;

        if (token != null && !token.isBlank()) {
            UserSession.startSession(token, null);
            try {
                AuthService authService = new AuthService();
                Users user = authService.getUserByToken();
                UserSession.setCurrentUser(user);
                validToken = true;
            } catch (Exception e) {
                UserSession.cleanSession();
                TokenStorage.clearToken();
            }
        }

        if (validToken) {
            root = FXMLLoader.load(App.class.getResource("/com.sportt5/view/view.fxml"));
        } else {
            root = FXMLLoader.load(App.class.getResource("/com.sportt5/view/auth/auth-view.fxml"));
        }
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

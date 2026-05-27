package com.sportt5.controller;

import com.sportt5.App;
import com.sportt5.session.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AccountController {

    @FXML
    public void initialize() {
    }
    @FXML
    public void handleSignOut(ActionEvent event) {
        UserSession.cleanSession();

        try {
            Parent root = FXMLLoader.load(App.class.getResource("/com.sportt5/view/auth/auth-view.fxml"));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1220, 810);
            scene.getStylesheets().add(App.class.getResource("/com.sportt5/css/style.css").toExternalForm());

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

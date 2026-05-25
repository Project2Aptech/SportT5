package com.sportt5.controller;

import com.sportt5.model.AuthResponse;
import com.sportt5.service.AuthService;
import com.sportt5.session.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
public class AuthController {
    @FXML
    private VBox loginCard;
    @FXML
    private VBox signupCard;
    @FXML
    private TextField loginEmailField;
    @FXML
    private PasswordField loginPasswordField;
    @FXML
    private CheckBox rememberMeCheckbox;
    @FXML
    private Label loginStatusLabel;

    private final AuthService authService = new AuthService();

    @FXML
    public void initialize() {
    }
    @FXML
    private void handleShowSignUp(ActionEvent event) {
        loginCard.setVisible(false);
        loginCard.setManaged(false);
        signupCard.setVisible(true);
        signupCard.setManaged(true);
    }

    @FXML
    private void handleShowLogin(ActionEvent event) {
        signupCard.setVisible(false);
        signupCard.setManaged(false);
        loginCard.setVisible(true);
        loginCard.setManaged(true);
    }

    @FXML
    public void handleSignIn(ActionEvent event) {
        String loginInput = loginEmailField.getText();
        String passwordInput = loginPasswordField.getText();

        if(loginInput == null || loginInput.trim().isEmpty() || passwordInput == null || passwordInput.isEmpty()) {
            loginStatusLabel.setText("Please enter your email or password");
            return;
        }
        loginStatusLabel.setText("Waiting sever check...");

    new Thread(()->{
        try{
            AuthResponse response = authService.authenticate(loginInput.trim(), passwordInput);

            javafx.application.Platform.runLater(()->{
                UserSession.startSession(response.getToken(), response.getUser());
                loginStatusLabel.setText("Login success!");
                switchToMainScene(event);
            });
        }catch (Exception e){
            javafx.application.Platform.runLater(()->{
                loginStatusLabel.setText(e.getMessage() != null ? e.getMessage() : "Error no connection server!");
            });
        }
    }).start();
    }
    public void switchToMainScene(ActionEvent event) {
        try  {
            Parent root = FXMLLoader.load(getClass().getResource("/com.sportt5/view/view.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 1220, 810);
            scene.getStylesheets().add(getClass().getResource("/com.sportt5/css/style.css").toExternalForm());

            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
            loginStatusLabel.setText("Error main screen" + e.getMessage());
        }
    }




}
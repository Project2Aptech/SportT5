package com.sportt5.controller.components;

import com.sportt5.model.Users;
import com.sportt5.service.AuthService;
import com.sportt5.session.UserSession;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ResetPasswordController {
    @FXML private TextField tokenField;
    @FXML private Label errorText;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    private final AuthService authService = new AuthService();

    public void initData(String token){
        tokenField.setText(token);
        tokenField.setVisible(false);
        tokenField.setManaged(false);
    }

    @FXML
    private void handleConfirm() {
        String token = tokenField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (token.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            errorText.setText("Please fill all fields");
            return;
        }
        if (!password.equals(confirmPassword)) {
            errorText.setText("Passwords do not match");
            return;
        }

        try {
            boolean resetPasswordCheck = authService.resetPassword(token, password);

            Platform.runLater(() -> {
                Alert alert = new Alert(
                        Alert.AlertType.INFORMATION
                );

                alert.setHeaderText("Success");
                alert.setContentText(
                        "Password reset successfully"
                );

                alert.showAndWait();
            });

            tokenField.setVisible(true);
            tokenField.setManaged(true);
            handleCancel();

        } catch (Exception e) {
            throw  new RuntimeException(e);
        }
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) tokenField.getScene().getWindow();
        stage.close();
    }
}
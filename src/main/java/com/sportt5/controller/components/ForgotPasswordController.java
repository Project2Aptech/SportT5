package com.sportt5.controller.components;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportt5.service.AuthService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.http.HttpResponse;

public class ForgotPasswordController {
    private ObjectMapper mapper = new ObjectMapper();

    @FXML private TextField emailField;
    @FXML private Label errorText;
    private final AuthService authService = new AuthService();

    @FXML private void handleContinue() {
        String email = emailField.getText();

        if (email == null || email.isBlank()) {
            errorText.setText("Please enter email");
            return;
        }
        try {
            HttpResponse<String> sendEmail = authService.forgotPassword(email);
            if(sendEmail.statusCode()==204) {
            Platform.runLater(() -> {
                new Alert(
                        Alert.AlertType.INFORMATION, "Reset token has been sent to your email."
                ).showAndWait();
            });
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com.sportt5/view/components/reset-password.fxml"
                    )
            );

            Parent root = loader.load();

            Scene scene = new Scene(root);

            scene.getStylesheets().add(
                    getClass()
                            .getResource("/com.sportt5/css/account.css")
                            .toExternalForm()
            );

            Stage stage = new Stage();
            stage.setTitle("Reset Password");
            stage.setScene(scene);
            stage.show();

            handleCancel();
            }
            else{
                JsonNode node = mapper.readTree(sendEmail.body());
                errorText.setText(
                        node.get("message").asText()
                );            }
        } catch (Exception e) {
            throw  new RuntimeException(e);
        }
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) emailField.getScene().getWindow();

        stage.close();
    }
}
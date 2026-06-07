package com.sportt5.controller.components;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportt5.model.Users;
import com.sportt5.service.AuthService;
import com.sportt5.session.UserSession;
import com.sportt5.util.ApiClient;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PaymentController {
    @FXML
    private Label planLabel;
    @FXML private Label errorLabel;
    @FXML private Button confirmBtn;
    private String selectedTier;
    private Users currentUser;
    private final AuthService authService = new AuthService();
    @FXML
    private ImageView qrImageView;

    @FXML
    public void initialize() {
        var qrUrl = getClass().getResource("/com.sportt5/img/qrCode.png");
        if (qrUrl != null) {
            qrImageView.setImage(new Image(qrUrl.toExternalForm()));
        } else {
            System.err.println("[WARN] QR code image not found at /com.sportt5/img/qrCode.png");
        }
    }

    public void initData(String tier, String price, Users user) {
        this.selectedTier = tier;
        this.currentUser = user;
        planLabel.setText("Buy a package: " + tier + " – $" + price + " / month");
    }

    @FXML
    private void onConfirm() {
        String planType = selectedTier;
        int userId = currentUser.getId();

        new Thread(() -> {
            try {
                String paymentUrl = authService.paymentPlan(selectedTier, userId);

                Desktop.getDesktop().browse(
                        URI.create(paymentUrl)
                );

                Alert alert = new Alert(
                        Alert.AlertType.INFORMATION
                );
                alert.setHeaderText("Payment");
                alert.setContentText(
                        "Complete payment in browser, then click OK."
                );

                alert.showAndWait();

            } catch (Exception e) {
                Platform.runLater(() ->
                        errorLabel.setText("Error: " + e.getMessage())
                );
            }
        }).start();
    }


    @FXML
    private void onCancel() {
        planLabel.getScene()
                .getWindow()
                .hide();
    }
}
package com.sportt5.controller.components;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportt5.model.Users;
import com.sportt5.service.AuthService;
import com.sportt5.session.UserSession;
import com.sportt5.util.ApiClient;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

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
        var qrUrl = getClass().getResource("/com/sportt5/images/qrCode.png");
        if (qrUrl != null) {
            qrImageView.setImage(new Image(qrUrl.toExternalForm()));
        } else {
            System.err.println("[WARN] QR code image not found at /com/sportt5/images/qrCode.png");
        }
    }

    public void initData(String tier, String price, Users user) {
        this.selectedTier = tier;
        this.currentUser = user;
        planLabel.setText("Mua gói: " + tier + " – $" + price + " / tháng");
    }

    @FXML
    private void onConfirm() {
        errorLabel.setText("");

        new Thread(() -> {
            try {
                Map<String, Object> updates = new HashMap<>();
                updates.put("accountType", selectedTier);

                Users userCurrent = authService.updateUser(currentUser.getId(), updates);
                System.out.println("Payment updated user: " + userCurrent);
                if (userCurrent != null && userCurrent.getAccountType() != null) {
                    System.out.println("New account type after payment: " + userCurrent.getAccountType().name());
                }
                Platform.runLater(() -> {
                    if (userCurrent != null) {
                        UserSession.getInstance().setCurrentUser(userCurrent);

                        errorLabel.setText("Payment success");
                        Stage stage =
                                (Stage) confirmBtn.getScene().getWindow();
                        stage.close();

                    } else {
                        errorLabel.setText("Payment failed");
                    }
                });

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
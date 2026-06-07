package com.sportt5.controller.components;

import com.sportt5.model.Users;
import com.sportt5.service.AuthService;
import com.sportt5.session.UserSession;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.net.URI;

public class SubscriptionController {
    @FXML private VBox normalCard, proCard, premiumCard;
    @FXML private Button normalBtn;
    @FXML private Button proBtn;
    @FXML private Button premiumBtn;
    private Users currentUser;

    private final AuthService authService = new AuthService();

    public void initData(Users user) {
        this.currentUser = user;
        highlightCurrentPlan();
    }
    private void highlightCurrentPlan() {
        String tier = (currentUser.getAccountType() != null) ? currentUser.getAccountType().name() : "NORMAL";
        System.out.println(tier);
        normalBtn.setText("Buy");
        proBtn.setText("Buy");
        premiumBtn.setText("Buy");

        normalBtn.setDisable(false);
        proBtn.setDisable(false);
        premiumBtn.setDisable(false);

        normalCard.getStyleClass().remove("tier-active");
        proCard.getStyleClass().remove("tier-active");
        premiumCard.getStyleClass().remove("tier-active");


        switch (tier) {
            case "NORMAL" -> {
                normalBtn.setText("Current");
                normalBtn.setDisable(true);
                normalCard.getStyleClass().add("tier-active");
            }
            case "PRO" -> {
                proBtn.setText("Current");
                proBtn.setDisable(true);
                proCard.getStyleClass().add("tier-active");
            }
            case "PREMIUM" -> {
                premiumBtn.setText("Current");
                premiumBtn.setDisable(true);
                premiumCard.getStyleClass().add("tier-active");
            }
        }
    }
    @FXML
    private void handleBuyNormal() {
        openPaymentDialog("NORMAL", "0");
    }

    @FXML
    private void handleBuyPro() {
        openPaymentDialog("PRO", "9.99");
    }

    @FXML
    private void handleBuyPremium() {
        openPaymentDialog("PREMIUM", "14.99");
    }

    private void openPaymentDialog(String tierName, String price) {
        System.out.println("[DEBUG] openPaymentDialog called for tier=" + tierName + " price=" + price);
        try {
            new Thread(() -> {
                try {
                    String paymentUrl = authService.paymentPlan(tierName, currentUser.getId());

                    Desktop.getDesktop().browse(
                            URI.create(paymentUrl)
                    );

                    Platform.runLater(() -> {
                        Alert alert = new Alert(
                                Alert.AlertType.INFORMATION
                        );

                        alert.setHeaderText("Payment");
                        alert.setContentText(
                                "Complete payment in browser, then click OK."
                        );

                        alert.showAndWait();

                        checkPaymentResult();
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void checkPaymentResult() {
        new Thread(() -> {
            try {
                Users freshUser =
                        authService.getUserById(
                                currentUser.getId()
                        );

                Platform.runLater(() -> {

                    if (freshUser.getAccountType()
                            != currentUser.getAccountType()) {
                        UserSession.setCurrentUser(freshUser);

                        new Alert(
                                Alert.AlertType.INFORMATION,
                                "Payment successful!"
                        ).showAndWait();

                        Stage stage = (Stage)
                                proBtn
                                        .getScene()
                                        .getWindow();

                        stage.close();

                    } else {
                        new Alert(
                                Alert.AlertType.ERROR,
                                "Payment failed or cancelled."
                        ).showAndWait();
                    }
                });

            } catch (Exception e) {
                Platform.runLater(() ->
                        new Alert(
                                Alert.AlertType.ERROR,
                                e.getMessage()
                        ).showAndWait()
                );
            }
        }).start();
    }



}

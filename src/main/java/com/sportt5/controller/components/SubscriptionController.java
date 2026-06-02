package com.sportt5.controller.components;

import com.sportt5.model.Users;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SubscriptionController {
    @FXML private VBox normalCard, proCard, premiumCard;
    @FXML private Button normalBtn;
    @FXML private Button proBtn;
    @FXML private Button premiumBtn;
    private Users currentUser;

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.sportt5/view/users/payment-dialog.fxml"));
            DialogPane pane = loader.load();
            Dialog<Void> dialog = new Dialog<>();
            dialog.setDialogPane(pane);

            Stage owner = null;
            if (normalCard.getScene() != null) {
                owner = (Stage) normalCard.getScene().getWindow();
            }
            if (owner == null && proBtn.getScene() != null) {
                owner = (Stage) proBtn.getScene().getWindow();
            }
            if (owner == null && premiumBtn.getScene() != null) {
                owner = (Stage) premiumBtn.getScene().getWindow();
            }
            if (owner != null) {
                dialog.initOwner(owner);
            }
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initStyle(StageStyle.UTILITY);

            PaymentController ctrl = loader.getController();
            ctrl.initData(tierName, price, currentUser);
            dialog.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

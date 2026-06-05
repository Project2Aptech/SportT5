package com.sportt5.controller.pages;

import com.sportt5.model.*;
import com.sportt5.service.AdminService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class AdminAnalyticsController {
    @FXML private GridPane userGrid;
    @FXML private Label totalProLabel;
    @FXML private Label totalPremiumLabel;
    @FXML private Label totalRevenueLabel;

    private final AdminService adminService = new AdminService();

    @FXML
    public void initialize() {
        loadAnalytics();
    }
    public void loadAnalytics(){
        new Thread(() -> {
            try {
                SubscriptionsResponse response = adminService.getSubscriptions();
                List<Subscriptions> subscriptions = response.getContent();

                int totalPro = 0;
                int totalPremium = 0;
                double totalAmount = 0;

                for (Subscriptions s : subscriptions) {
                    if (s.getPlanType() != null) {
                        switch (s.getPlanType().name()) {
                            case "PRO" -> totalPro++;
                            case "PREMIUM" -> totalPremium++;
                        }
                    }

                    if (s.getAmount() != null) {
                        totalAmount += s.getAmount().doubleValue();
                    }
                }

                int finalTotalPremium = totalPremium;
                int finalTotalPro = totalPro;
                double finalTotalAmount = totalAmount;
                Platform.runLater(() -> {
                    totalProLabel.setText(String.valueOf(finalTotalPro));
                    totalPremiumLabel.setText(String.valueOf(finalTotalPremium));
                    totalRevenueLabel.setText(String.format("$%.2f", finalTotalAmount));
                    renderDashboard(subscriptions);
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
    private void renderDashboard(List<Subscriptions> sub) {
        userGrid.getChildren().removeIf(node -> {
            Integer row = GridPane.getRowIndex(node);
            return row != null && row > 0;
        });

        int row = 1;
        for (Subscriptions s : sub) {
            Label idLabel = new Label(String.valueOf(s.getId()));
            idLabel.getStyleClass().add("table-text");

            Label userIdLabel = new Label(String.valueOf(s.getUserId()));
            userIdLabel.getStyleClass().add("table-text");

            Label planLabel = new Label(
                    s.getPlanType() != null ? s.getPlanType().name() : "NORMAL"
            );

            planLabel.getStyleClass().add(
                    switch (planLabel.getText()) {
                        case "PRO" -> "plan-pro";
                        case "PREMIUM" -> "plan-premium";
                        default -> "plan-normal";
                    }
            );

            Label amountLabel = new Label(
                    "$"+s.getAmount().toString()
            );
            amountLabel.getStyleClass().add("table-text");

            Label statusLabel = new Label(s.getStatus());

            statusLabel.getStyleClass().add(
                    switch (s.getStatus()) {
                        case "ACTIVE" -> "status-active";
                        case "EXPIRED" -> "status-inactive";
                        case "CANCELLED" -> "status-inactive";
                        default -> "status-inactive";
                    }
            );

            Label startLabel = new Label(
                    s.getStartedAt() != null
                            ? s.getStartedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                            : "-"
            );
            startLabel.getStyleClass().add("table-text");

            Label expireLabel = new Label(
                    s.getExpiresAt() != null
                            ? s.getExpiresAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                            : "-"
            );
            expireLabel.getStyleClass().add("table-text");

            userGrid.add(idLabel, 0, row);
            userGrid.add(userIdLabel, 1, row);
            userGrid.add(planLabel, 2, row);
            userGrid.add(amountLabel, 3, row);
            userGrid.add(statusLabel, 4, row);
            userGrid.add(startLabel, 5, row);
            userGrid.add(expireLabel, 6, row);

            row++;
        }




    }


}

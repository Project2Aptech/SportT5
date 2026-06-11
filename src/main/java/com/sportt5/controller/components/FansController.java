package com.sportt5.controller.components;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportt5.util.ApiClient;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FansController {

    @FXML private GridPane fansGrid;
    @FXML private Label emptyLabel;

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final DateTimeFormatter INPUT_FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private static final DateTimeFormatter OUTPUT_FMT =
            DateTimeFormatter.ofPattern("MMM yyyy");

    @FXML
    public void initialize() {
        loadFans();
    }

    public void loadFans() {
        new Thread(() -> {
            try {
                HttpResponse<String> response = ApiClient.get("artists/followers");
                JsonNode root = mapper.readTree(response.body());
                JsonNode content = root.get("content");

                Platform.runLater(() -> renderFans(content));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void renderFans(JsonNode content) {
        fansGrid.getChildren().removeIf(node -> {
            Integer row = GridPane.getRowIndex(node);
            return row != null && row > 0;
        });

        if (content == null || content.isEmpty()) {
            emptyLabel.setVisible(true);
            emptyLabel.setManaged(true);
            return;
        }

        emptyLabel.setVisible(false);
        emptyLabel.setManaged(false);

        int row = 1;
        for (JsonNode fan : content) {
            String displayName = fan.path("displayName").asText("Unknown");
            String avatarUrl   = fan.path("avatarUrl").asText(null);
            String followedAt  = fan.path("followedAt").asText(null);

            // ── NAME column ──────────────────────────────────────────────
            HBox nameBox = new HBox(12);
            nameBox.setAlignment(Pos.CENTER_LEFT);

            StackPane thumb = buildAvatar(avatarUrl);

            Label nameLabel = new Label(displayName);
            nameLabel.getStyleClass().add("table-title");

            nameBox.getChildren().addAll(thumb, nameLabel);

            // ── SINCE column ─────────────────────────────────────────────
            String sinceText = "—";
            if (followedAt != null && !followedAt.isBlank()) {
                try {
                    sinceText = LocalDateTime.parse(followedAt, INPUT_FMT)
                            .format(OUTPUT_FMT);
                } catch (Exception ignored) {}
            }
            Label sinceLabel = new Label(sinceText);
            sinceLabel.getStyleClass().add("table-text");

            fansGrid.add(nameBox,   0, row);
            fansGrid.add(sinceLabel, 1, row);

            row++;
        }
    }

    private StackPane buildAvatar(String url) {
        StackPane thumb = new StackPane();
        thumb.setPrefSize(34, 34);

        if (url != null && !url.isBlank()) {
            try {
                ImageView iv = new ImageView(new Image(url, 34, 34, true, true, true));
                iv.setFitWidth(34);
                iv.setFitHeight(34);

                Circle clip = new Circle(17, 17, 17);
                iv.setClip(clip);

                thumb.getChildren().add(iv);
                return thumb;
            } catch (Exception ignored) {}
        }

        thumb.getStyleClass().addAll("admin-thumb", "thumb-gray");
        return thumb;
    }
}
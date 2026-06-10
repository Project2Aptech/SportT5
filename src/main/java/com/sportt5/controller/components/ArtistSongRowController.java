package com.sportt5.controller.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;

public class ArtistSongRowController {

    @FXML public VBox root;
    @FXML private TextField titleField;
    @FXML private Label audioLabel;
    @FXML private StackPane coverZone;
    @FXML private ImageView coverPreview;
    @FXML private Label coverLabel;
    @FXML private ProgressBar progressBar;
    @FXML private Label statusLabel;

    private File audioFile;
    private File coverFile;
    private Runnable onRemove;

    public void setOnRemove(Runnable r) { this.onRemove = r; }

    @FXML
    private void handleRemove() {
        if (onRemove != null) onRemove.run();
    }

    @FXML
    private void handleAudioPick() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select Audio File");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav", "*.flac"));
        File f = fc.showOpenDialog(root.getScene().getWindow());
        if (f != null) setAudioFile(f);
    }

    @FXML
    private void handleCoverPick() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select Cover Image");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png"));
        File f = fc.showOpenDialog(root.getScene().getWindow());
        if (f != null) setCoverFile(f);
    }

    public void setAudioFile(File f) {
        audioFile = f;
        audioLabel.setText(f.getName());
        // Auto-fill title from filename if blank
        if (titleField.getText().isBlank()) {
            String name = f.getName();
            int dot = name.lastIndexOf('.');
            titleField.setText(dot > 0 ? name.substring(0, dot) : name);
        }
    }

    public void setCoverFile(File f) {
        coverFile = f;
        coverLabel.setVisible(false);
        coverLabel.setManaged(false);
        coverPreview.setImage(new Image(f.toURI().toString()));
        coverPreview.setVisible(true);
        coverPreview.setManaged(true);
    }

    // Drag & drop wiring (called from UploadController after load)
    public void setupDragDrop() {
        // Audio zone
        audioLabel.getParent().setOnDragOver(e -> {
            if (e.getDragboard().hasFiles()) e.acceptTransferModes(javafx.scene.input.TransferMode.COPY);
            e.consume();
        });
        audioLabel.getParent().setOnDragDropped(e -> {
            var files = e.getDragboard().getFiles();
            if (!files.isEmpty()) setAudioFile(files.get(0));
            e.setDropCompleted(true);
            e.consume();
        });

        // Cover zone
        coverZone.setOnDragOver(e -> {
            if (e.getDragboard().hasFiles()) e.acceptTransferModes(javafx.scene.input.TransferMode.COPY);
            e.consume();
        });
        coverZone.setOnDragDropped(e -> {
            var files = e.getDragboard().getFiles();
            if (!files.isEmpty()) setCoverFile(files.get(0));
            e.setDropCompleted(true);
            e.consume();
        });
    }

    // Getters used by UploadController
    public String getTitle()    { return titleField.getText().trim(); }
    public File   getAudioFile(){ return audioFile; }
    public File   getCoverFile(){ return coverFile; }

    public void setProgress(double value) {
        progressBar.setVisible(true);
        progressBar.setManaged(true);
        progressBar.setProgress(value);
    }

    public void setStatus(String msg) {
        statusLabel.setText(msg);
        statusLabel.setVisible(true);
        statusLabel.setManaged(true);
    }
}
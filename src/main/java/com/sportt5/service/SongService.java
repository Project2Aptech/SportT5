package com.sportt5.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sportt5.model.Songs;
import com.sportt5.session.UserSession;
import com.sportt5.util.ApiClient;

import java.io.File;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SongService {

    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /** Returns all songs belonging to the given artist. */
    public List<Songs> getSongsByArtist(int artistId) {
        try {
            HttpResponse<String> response = ApiClient.get("songs/artist/" + artistId);
            if (response.statusCode() != 200) return Collections.emptyList();

            JsonNode root = mapper.readTree(response.body());
            JsonNode content = root.path("content");
            return mapper.readValue(
                    content.toString(),
                    new TypeReference<List<Songs>>() {}
            );
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /** Soft-deletes a song by ID. Returns true on success. */
    public boolean deleteSong(int songId) {
        try {
            var response = ApiClient.delete("songs/" + songId);
            return response.statusCode() == 200 || response.statusCode() == 204;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Publishes a PENDING song. Returns true on success. */
    public boolean publishSong(int songId) {
        try {
            var response = ApiClient.patch("songs/" + songId + "/publish");
            return response.statusCode() == 200 || response.statusCode() == 204;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateSong(int songId, String title, int albumId,
                              int durationSeconds, int trackNumber,
                              String requiredAccountType, String status) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> body = new java.util.HashMap<>();
            body.put("artistId", UserSession.getInstance().getCurrentUserId());
            body.put("albumId", albumId);
            body.put("title", title);
            body.put("durationSeconds", durationSeconds);
            body.put("trackNumber", trackNumber);
            body.put("requiredAccountType", requiredAccountType);
            body.put("status", status);

            String json = mapper.writeValueAsString(body);
            System.out.println("PUT /songs/" + songId + " → " + json);

            var response = ApiClient.put("songs/" + songId, json);
            System.out.println("Response: " + response.statusCode() + " " + response.body());
            return response.statusCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Creates a new song via multipart POST.
     * Returns the created song's ID, or -1 on failure.
     */
    public int createSong(String title, int albumId, int durationSeconds,
                          String requiredAccountType, File audioFile) {
        try {
            String boundary = "----Boundary" + System.currentTimeMillis();

            // Build the JSON metadata part
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> meta = new java.util.HashMap<>();
            meta.put("artistId", UserSession.getInstance().getCurrentUserId());
            meta.put("albumId", albumId);
            meta.put("title", title);
            meta.put("durationSeconds", durationSeconds);
            meta.put("trackNumber", 1);
            meta.put("requiredAccountType", requiredAccountType);
            String json = mapper.writeValueAsString(meta);

            // Build multipart body
            var out = new java.io.ByteArrayOutputStream();
            // -- data part (JSON metadata)
            out.write(("--" + boundary + "\r\n").getBytes());
            out.write(("Content-Disposition: form-data; name=\"data\"\r\n").getBytes());
            out.write(("Content-Type: application/json\r\n\r\n").getBytes());
            out.write(json.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            out.write("\r\n".getBytes());
            // -- file part (audio)
            out.write(("--" + boundary + "\r\n").getBytes());
            out.write(("Content-Disposition: form-data; name=\"file\"; filename=\""
                    + audioFile.getName() + "\"\r\n").getBytes());
            out.write(("Content-Type: audio/mpeg\r\n\r\n").getBytes());
            out.write(java.nio.file.Files.readAllBytes(audioFile.toPath()));
            out.write("\r\n".getBytes());
            out.write(("--" + boundary + "--\r\n").getBytes());

            var response = ApiClient.postMultipart("songs", boundary, out.toByteArray());
            System.out.println("createSong → " + response.statusCode() + " " + response.body());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                JsonNode root = new ObjectMapper().readTree(response.body());
                return root.path("id").asInt(-1);
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Uploads a cover image for an existing song.
     * Returns true on success.
     */
    public boolean uploadSongCover(int songId, File coverFile) {
        try {
            String boundary = "----Boundary" + System.currentTimeMillis();
            String mimeType = coverFile.getName().endsWith(".png") ? "image/png" : "image/jpeg";

            var out = new java.io.ByteArrayOutputStream();
            out.write(("--" + boundary + "\r\n").getBytes());
            out.write(("Content-Disposition: form-data; name=\"file\"; filename=\""
                    + coverFile.getName() + "\"\r\n").getBytes());
            out.write(("Content-Type: " + mimeType + "\r\n\r\n").getBytes());
            out.write(java.nio.file.Files.readAllBytes(coverFile.toPath()));
            out.write("\r\n".getBytes());
            out.write(("--" + boundary + "--\r\n").getBytes());

            var response = ApiClient.postMultipart("songs/" + songId + "/cover",
                    boundary, out.toByteArray());
            System.out.println("uploadCover → " + response.statusCode());
            return response.statusCode() == 200 || response.statusCode() == 204;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
package com.sportt5.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sportt5.model.Albums;
import com.sportt5.session.UserSession;
import com.sportt5.util.ApiClient;

import java.io.File;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AlbumService {

    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /** Returns all albums belonging to the given artist. */
    public List<Albums> getAlbumsByArtist(int artistId) {
        try {
            HttpResponse<String> response = ApiClient.get("albums/artist/" + artistId);
            if (response.statusCode() != 200) return Collections.emptyList();

            JsonNode root = mapper.readTree(response.body());
            JsonNode content = root.path("content");
            return mapper.readValue(
                    content.toString(),
                    new TypeReference<List<Albums>>() {}
            );
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public boolean deleteAlbum(int albumId) {
        try {
            var response = ApiClient.delete("albums/" + albumId);
            return response.statusCode() == 200 || response.statusCode() == 204;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Creates a new album. Returns the created Albums object (with server-assigned ID),
     * or null on failure.
     */
    public Albums createAlbum(String title, LocalDate releaseDate) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            Map<String, Object> body = new java.util.HashMap<>();
            body.put("artistId", UserSession.getInstance().getCurrentUserId());
            body.put("title", title);
            body.put("releaseDate", releaseDate.toString()); // "2024-01-01"
            body.put("coverUrl", "");

            String json = mapper.writeValueAsString(body);
            var response = ApiClient.post("albums", json);
            System.out.println("createAlbum → " + response.statusCode() + " " + response.body());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                return mapper.readValue(response.body(), Albums.class);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Uploads a cover image for an existing album.
     * Returns true on success.
     */
    public boolean uploadAlbumCover(int albumId, File coverFile) {
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

            var response = ApiClient.postMultipart(
                    "albums/" + albumId + "/cover", boundary, out.toByteArray());
            System.out.println("uploadAlbumCover → " + response.statusCode());
            return response.statusCode() == 200 || response.statusCode() == 204;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
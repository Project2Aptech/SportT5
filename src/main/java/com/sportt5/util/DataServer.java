package com.sportt5.util;

import com.sportt5.session.UserSession;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.util.ArrayList;

public class DataServer {

    private static final String API_URL = "http://localhost:8080/api/v1/";

    // =========================
    // GET (with optional auth)
    // =========================
    public static HttpResponse<String> get(String endpoint) throws IOException, InterruptedException {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + endpoint))
                .GET()
                .header("Content-Type", "application/json");
        withAuth(builder);
        return ServerConnect.getClient().send(builder.build(), HttpResponse.BodyHandlers.ofString());
    }

    // =========================
    // POST
    // =========================
    public static HttpResponse<String> post(String endpoint, String jsonBody) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + endpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        return ServerConnect.getClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    // =========================
    // PUT
    // =========================
    public static HttpResponse<String> put(String endpoint, String jsonBody) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + endpoint))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        return ServerConnect.getClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    // =========================
    // PATCH (with auth)
    // =========================
    public static HttpResponse<String> patch(String endpoint, String jsonBody) throws IOException, InterruptedException {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + endpoint))
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonBody));
        withAuth(builder);
        return ServerConnect.getClient().send(builder.build(), HttpResponse.BodyHandlers.ofString());
    }

    // =========================
    // DELETE
    // =========================
    public static HttpResponse<String> delete(String endpoint) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + endpoint))
                .DELETE()
                .header("Content-Type", "application/json")
                .build();
        return ServerConnect.getClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    // =========================
    // Upload Avatar (multipart, with auth)
    // =========================
    public static HttpResponse<String> uploadAvatar(String endpoint, File file) throws IOException, InterruptedException {
        String boundary = "----Boundary" + System.currentTimeMillis();
        var byteArrays = new ArrayList<byte[]>();
        byteArrays.add((
                "--" + boundary + "\r\n" +
                "Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"\r\n" +
                "Content-Type: image/jpeg\r\n\r\n"
        ).getBytes());
        byteArrays.add(Files.readAllBytes(file.toPath()));
        byteArrays.add(("\r\n--" + boundary + "--\r\n").getBytes());

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + endpoint))
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .POST(HttpRequest.BodyPublishers.ofByteArrays(byteArrays));
        withAuth(builder);
        return ServerConnect.getClient().send(builder.build(), HttpResponse.BodyHandlers.ofString());
    }

    // =========================
    // Authenticated GET
    // =========================
    public static String getAuth(String endpoint) throws IOException, InterruptedException {
        HttpRequest request = authBuilder(endpoint).GET().build();
        return ServerConnect.getClient().send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // =========================
    // Authenticated POST
    // =========================
    public static HttpResponse<String> postAuth(String endpoint, String jsonBody) throws IOException, InterruptedException {
        HttpRequest request = authBuilder(endpoint)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        return ServerConnect.getClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    // =========================
    // Authenticated PUT
    // =========================
    public static String putAuth(String endpoint, String jsonBody) throws IOException, InterruptedException {
        HttpRequest request = authBuilder(endpoint)
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        return ServerConnect.getClient().send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // =========================
    // Authenticated DELETE
    // =========================
    public static String deleteAuth(String endpoint) throws IOException, InterruptedException {
        HttpRequest request = authBuilder(endpoint).DELETE().build();
        return ServerConnect.getClient().send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    private static HttpRequest.Builder withAuth(HttpRequest.Builder builder) {
        String token = UserSession.getInstance() != null ? UserSession.getInstance().getToken() : null;
        if (token != null && !token.isBlank()) {
            builder.header("Authorization", "Bearer " + token);
        }
        return builder;
    }

    private static HttpRequest.Builder authBuilder(String endpoint) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + endpoint))
                .header("Content-Type", "application/json");
        UserSession session = UserSession.getInstance();
        if (session != null && session.getToken() != null) {
            builder.header("Authorization", "Bearer " + session.getToken());
        }
        return builder;
    }
}

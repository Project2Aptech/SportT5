package com.sportt5.service;

import com.sportt5.session.UserSession;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConnectionServer {

    private static final String API_URL = "http://localhost:8080/api/v1/";

    private static final HttpClient client = HttpClient.newHttpClient();

    private static HttpRequest.Builder withAuth(HttpRequest.Builder builder) {
        String token = UserSession.getInstance() != null ? UserSession.getInstance().getToken() : null;
        if (token != null && !token.isBlank()) {
            builder.header("Authorization", "Bearer " + token);
        }
        return builder;
    }
    // =========================
    // GET
    // =========================
    public static HttpResponse<String> get(String endpoint)
            throws IOException, InterruptedException {

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + endpoint))
                .GET()
                .header("Content-Type", "application/json");

        // Thêm token nếu có
        builder = withAuth(builder);

        HttpRequest request = builder.build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    // =========================
    // POST
    // =========================
    public static HttpResponse<String> post(String endpoint, String jsonBody)
            throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + endpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }

    // =========================
    // PUT
    // =========================
    public static HttpResponse<String> put(String endpoint, String jsonBody)
            throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + endpoint))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }
    // =========================
    // PATCH
    // =========================
    public static HttpResponse<String> patch(String endpoint, String jsonBody)
            throws IOException, InterruptedException {

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + endpoint))
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonBody));

        // **Thêm token**
        builder = withAuth(builder);

        HttpRequest request = builder.build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    // =========================
    // DELETE
    // =========================
    public static String delete(String endpoint)
            throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + endpoint))
                .DELETE()
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}
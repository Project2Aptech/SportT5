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

    // =========================
    // GET
    // =========================
    public static String get(String endpoint) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + endpoint))
                .GET()
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
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
    public static String put(String endpoint, String jsonBody)
            throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + endpoint))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    // =========================
    // Authenticated GET
    // =========================
    public static String getAuth(String endpoint) throws IOException, InterruptedException {
        HttpRequest request = authBuilder(endpoint).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // =========================
    // Authenticated POST
    // =========================
    public static HttpResponse<String> postAuth(String endpoint, String jsonBody)
            throws IOException, InterruptedException {
        HttpRequest request = authBuilder(endpoint)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    // =========================
    // Authenticated PUT
    // =========================
    public static String putAuth(String endpoint, String jsonBody)
            throws IOException, InterruptedException {
        HttpRequest request = authBuilder(endpoint)
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // =========================
    // Authenticated DELETE
    // =========================
    public static String deleteAuth(String endpoint) throws IOException, InterruptedException {
        HttpRequest request = authBuilder(endpoint).DELETE().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
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
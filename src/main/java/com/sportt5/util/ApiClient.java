package com.sportt5.util;

import com.sportt5.session.UserSession;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {

    private static final String BASE_URL = "http://localhost:8080/api/v1/";
    private static final String SERVER_ROOT = "http://localhost:8080/";

    public static String resolveUrl(String url) {
        if (url == null || url.isBlank()) return null;
        return url.startsWith("http") ? url : SERVER_ROOT + url;
    }
    private static final HttpClient client = HttpClient.newHttpClient();

    public static HttpClient getClient() {
        return client;
    }

    public static HttpResponse<String> get(String endpoint) throws IOException, InterruptedException {
        HttpRequest request = builder(endpoint).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static HttpResponse<String> post(String endpoint, String jsonBody) throws IOException, InterruptedException {
        HttpRequest request = builder(endpoint)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static HttpResponse<String> put(String endpoint, String jsonBody) throws IOException, InterruptedException {
        HttpRequest request = builder(endpoint)
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static HttpResponse<String> patch(String endpoint, String jsonBody) throws IOException, InterruptedException {
        HttpRequest request = builder(endpoint)
                .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static HttpResponse<String> delete(String endpoint) throws IOException, InterruptedException {
        HttpRequest request = builder(endpoint).DELETE().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static HttpRequest.Builder builder(String endpoint) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Content-Type", "application/json");
        UserSession session = UserSession.getInstance();
        if (session != null && session.getToken() != null && !session.getToken().isBlank()) {
            builder.header("Authorization", "Bearer " + session.getToken());
        }
        return builder;
    }
}

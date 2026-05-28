package com.sportt5.service;

import com.sportt5.session.UserSession;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.util.ArrayList;

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
    public static HttpResponse<String> get1(String endpoint)
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
    public static HttpResponse<String> post1(String endpoint, String jsonBody)
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
    public static HttpResponse<String> put1(String endpoint, String jsonBody)
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

        builder = withAuth(builder);

        HttpRequest request = builder.build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
    public static HttpResponse<String> uploadAvatar(
            String endpoint,
            File file
    ) throws IOException, InterruptedException {

        String boundary =
                "----Boundary" + System.currentTimeMillis();

        var byteArrays = new ArrayList<byte[]>();

        byteArrays.add((
                "--" + boundary + "\r\n" +
                        "Content-Disposition: form-data; name=\"file\"; filename=\"" +
                        file.getName() + "\"\r\n" +
                        "Content-Type: image/jpeg\r\n\r\n"
        ).getBytes());

        byteArrays.add(Files.readAllBytes(file.toPath()));
        byteArrays.add((
                "\r\n--" + boundary + "--\r\n"
        ).getBytes());

        HttpRequest.Builder builder =
                HttpRequest.newBuilder()
                        .uri(URI.create(API_URL + endpoint))
                        .header(
                                "Content-Type",
                                "multipart/form-data; boundary=" + boundary
                        )
                        .POST(
                                HttpRequest.BodyPublishers.ofByteArrays(byteArrays)
                        );

        builder = withAuth(builder);
        HttpRequest request = builder.build();
        return client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
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


}
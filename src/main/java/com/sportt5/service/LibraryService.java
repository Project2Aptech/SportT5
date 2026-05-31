package com.sportt5.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sportt5.model.Genres;
import com.sportt5.model.PageResponse;
import com.sportt5.model.Songs;
import com.sportt5.model.Users;
import com.sportt5.session.UserSession;
import com.sportt5.util.ApiClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LibraryService {
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public Map<Integer, String> getAllUsers() throws IOException, InterruptedException {
        HttpResponse<String> response = ApiClient.get("users");
        if (response.statusCode() == 200) {
            JavaType type = mapper.getTypeFactory().constructParametricType(PageResponse.class, Users.class);
            PageResponse<Users> page = mapper.readValue(response.body(), type);
            List<Users> users = page.getContent();
            return users.stream()
                    .collect(Collectors.toMap(
                            Users::getId,
                            user -> user.getDisplayName() != null ? user.getDisplayName() : "Unknown",
                            (name1, name2) -> name1
                    ));
        }
        throw new RuntimeException(mapper.readTree(response.body()).get("message").asText());
    }

    public Map<Integer, String> getAllGenres() throws IOException, InterruptedException {
        HttpResponse<String> response = ApiClient.get("genres");
        if (response.statusCode() == 200) {
            JavaType type = mapper.getTypeFactory().constructParametricType(PageResponse.class, Genres.class);
            PageResponse<Genres> page = mapper.readValue(response.body(), type);
            List<Genres> genres = page.getContent();
            return genres.stream()
                    .collect(Collectors.toMap(
                            Genres::getId,
                            genre -> genre.getName() != null ? genre.getName() : "Unknown",
                            (name1, name2) -> name1
                    ));
        }
        throw new RuntimeException(mapper.readTree(response.body()).get("message").asText());
    }

    public List<Songs> getLikedSongs() throws IOException, InterruptedException {
        String token = UserSession.getInstance().getToken();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/liked-songs"))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .GET()
                .build();

        return getResponse(request);
    }

    public List<Songs> getPlayHistory() throws IOException, InterruptedException {
        String token = UserSession.getInstance().getToken();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/history"))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .GET()
                .build();

        return getResponse(request);
    }

    private List<Songs> getResponse(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = ApiClient.getClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Status = " + response.statusCode());
        System.out.println("Body   = " + response.body());

        if (response.statusCode() == 200) {
            JavaType type = mapper.getTypeFactory().constructParametricType(PageResponse.class, Songs.class);
            PageResponse<Songs> page = mapper.readValue(response.body(), type);
            return page.getContent();
        }
        throw new RuntimeException(mapper.readTree(response.body()).get("message").asText());
    }
}

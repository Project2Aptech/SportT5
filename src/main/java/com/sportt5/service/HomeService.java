package com.sportt5.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sportt5.model.Albums;
import com.sportt5.model.PageResponse;
import com.sportt5.model.Songs;
import com.sportt5.util.ApiClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


public class HomeService {
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public HttpRequest createDownloadRequest(Songs song) {
        return HttpRequest.newBuilder()
                .uri(URI.create(song.getFileUrl()))
                .GET()
                .build();
    }

    public List<Albums> getHomeAlbums() throws IOException, InterruptedException {
        List<Albums> albums = getResponseWithoutToken("albums?size=10", Albums.class);
        if (albums == null || albums.isEmpty()) return java.util.Collections.emptyList();
        return albums;
    }

    public List<Songs> getHomeSingles() throws IOException, InterruptedException {
        List<Songs> songs = getResponseWithoutToken("songs?size=10", Songs.class);
        if (songs == null || songs.isEmpty()) return java.util.Collections.emptyList();
        return songs;
    }

    public boolean likeASong(int id) throws IOException, InterruptedException {
        HttpResponse<String> response = ApiClient.post(String.format("liked-songs/%d", id));
        return response.statusCode() == 200;
    }

    public boolean checkSongLikedStatus(int id) throws IOException, InterruptedException {
        HttpResponse<String> response = ApiClient.get(String.format("liked-songs/%d/status", id));
        if (response.statusCode() == 200) return mapper.readTree(response.body()).asBoolean();
        return false;
    }

    public boolean unlikeASong(int id) throws IOException, InterruptedException {
        HttpResponse<String> response = ApiClient.delete(String.format("liked-songs/%d", id));
        return response.statusCode() == 200;
    }

    //════════════════════Private methods to get API response════════════════════
    private <T> List<T> getResponseWithToken(HttpRequest request, Class<T> c) throws IOException, InterruptedException {
        HttpResponse<String> response = ApiClient.getClient().send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            JavaType type = mapper.getTypeFactory().constructParametricType(PageResponse.class, c);
            PageResponse<T> page = mapper.readValue(response.body(), type);
            return page.getContent();
        }
        throw new RuntimeException(mapper.readTree(response.body()).get("message").asText());
    }

    private <T> List<T> getResponseWithoutToken(String s, Class<T> c) throws IOException, InterruptedException {
        //For multiple objects {}
        HttpResponse<String> response = ApiClient.get(s);
        if (response.statusCode() == 200) {
            JavaType type = mapper.getTypeFactory().constructParametricType(PageResponse.class, c);
            PageResponse<T> page = mapper.readValue(response.body(), type);
            return page.getContent();
        }
        throw new RuntimeException(mapper.readTree(response.body()).get("message").asText());
    }

    private <T> List<T> getResponseWithoutToken2(String s, Class<T> c) throws IOException, InterruptedException {
        //For arrays []
        HttpResponse<String> response = ApiClient.get(s);
        if (response.statusCode() == 200) {
            JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, c);
            return mapper.readValue(response.body(), type);
        }
        throw new RuntimeException(mapper.readTree(response.body()).get("message").asText());
    }

    private <T> T getResponseWithoutToken3(String s, Class<T> c) throws IOException, InterruptedException {
        //For a single object {}
        HttpResponse<String> response = ApiClient.get(s);
        if (response.statusCode() == 200) return mapper.readValue(response.body(), c);
        throw new RuntimeException(mapper.readTree(response.body()).get("message").asText());
    }
}

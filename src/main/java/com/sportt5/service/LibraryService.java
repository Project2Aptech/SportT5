package com.sportt5.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sportt5.model.*;
import com.sportt5.session.UserSession;
import com.sportt5.util.ApiClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class LibraryService {
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public JsonNode getLikedSongs() throws IOException, InterruptedException {
        String token = UserSession.getInstance().getToken();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/liked-songs"))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = ApiClient.getClient().send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            JsonNode root = mapper.readTree(response.body());
            return root.get("content");
        }
        throw new RuntimeException(mapper.readTree(response.body()).get("message").asText());
    }

    public List<Playlists> getUserPlaylists() throws IOException, InterruptedException {
        String token = UserSession.getInstance().getToken();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/playlists/me"))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .GET()
                .build();
        List<Playlists> playlists = getResponseWithToken(request, Playlists.class);
        if (playlists == null || playlists.isEmpty()) return java.util.Collections.emptyList();
        return playlists;
    }

    public List<Songs> getPlaylistSongs(int id) throws IOException, InterruptedException {
        List<Songs> songs = getResponseWithoutToken(String.format("playlists/%d/songs", id), Songs.class);
        if (songs == null || songs.isEmpty()) return java.util.Collections.emptyList();
        return songs;
    }

    public List<Songs> getSongByGenre(Set<Integer> genreIds, boolean matchAll) throws IOException, InterruptedException {
        String idsParam = genreIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        if (idsParam.isEmpty()) return java.util.Collections.emptyList();
        List<Songs> songs = getResponseWithoutToken(String.format("songs/filter?genreIds=%s&matchAll=%b",idsParam, matchAll), Songs.class);
        if (songs == null || songs.isEmpty()) return java.util.Collections.emptyList();
        return songs;
    }

    public List<Songs> getAllSongs() throws IOException, InterruptedException {
        List<Songs> songs = getResponseWithoutToken("songs?size=1000", Songs.class);
        if (songs == null || songs.isEmpty()) return java.util.Collections.emptyList();
        return songs;
    }

    public Albums getAlbumDetails(int id) throws IOException, InterruptedException {
        return getResponseWithoutToken3(String.format("albums/%d", id), Albums.class);
    }

    public List<Songs> getSongByAlbum(int id) throws IOException, InterruptedException {
        List<Songs> songs = getResponseWithoutToken(String.format("songs/album/%d", id), Songs.class);
        if (songs == null || songs.isEmpty()) return java.util.Collections.emptyList();
        return songs;
    }

    public List<Users> getAllArtists() throws IOException, InterruptedException {
        List<Users> artists = getResponseWithoutToken("users/role/ARTIST", Users.class);
        if (artists == null || artists.isEmpty()) return java.util.Collections.emptyList();
        return artists;
    }

    public int getArtistFollowersCount(int id) throws IOException, InterruptedException {
        return getResponseWithoutToken3(String.format("artists/%d/followers/count", id), Integer.class);
    }

    public List<Songs> getSongByArtist(int id) throws IOException, InterruptedException {
        List<Songs> songs = getResponseWithoutToken(String.format("songs/artist/%d", id), Songs.class);
        if (songs == null || songs.isEmpty()) return java.util.Collections.emptyList();
        return songs;
    }

    public int getArtistAlbumsCount(int id) throws IOException, InterruptedException {
        List<Albums> albums = getResponseWithoutToken(String.format("albums/artist/%d", id), Albums.class);
        if (albums == null || albums.isEmpty()) return 0;
        return albums.size();
    }

    //════════════════════Supporting methods to get song details════════════════════
    public Map<Integer, String> getUsersMap() throws IOException, InterruptedException {
        List<Users> users = getResponseWithoutToken("users", Users.class);
        if (users == null || users.isEmpty()) return java.util.Collections.emptyMap();
        return users.stream()
                .collect(Collectors.toMap(
                        Users::getId,
                        user -> user.getDisplayName() != null ? user.getDisplayName() : "Unknown",
                        (name1, name2) -> name1
                ));
    }

    public Map<Integer, String> getAlbumsMap() throws IOException, InterruptedException {
        List<Albums> albums = getResponseWithoutToken("albums", Albums.class);
        if (albums == null || albums.isEmpty()) return java.util.Collections.emptyMap();
        return albums.stream()
                .collect(Collectors.toMap(
                        Albums::getId,
                        album -> album.getTitle() != null ? album.getTitle() : "Unknown",
                        (name1, name2) -> name1
                ));
    }

    public Map<Integer, String> getGenresMap() throws IOException, InterruptedException {
        List<Genres> genres = getResponseWithoutToken2("genres", Genres.class);
        if (genres == null || genres.isEmpty()) return java.util.Collections.emptyMap();
        return genres.stream()
                .collect(Collectors.toMap(
                        Genres::getId,
                        genre -> genre.getName() != null ? genre.getName() : "Unknown",
                        (name1, name2) -> name1
                ));
    }

    public List<Albums> getAllAlbums() throws IOException, InterruptedException {
        List<Albums> albums = getResponseWithoutToken("albums", Albums.class);
        if (albums == null || albums.isEmpty()) return java.util.Collections.emptyList();
        return albums;
    }

    //════════════════════Private methods to get API response════════════════════
    private <T> List<T> getResponseWithToken(HttpRequest request, Class<T> c) throws IOException, InterruptedException {
        HttpResponse<String> response = ApiClient.getClient().send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println("Status = " + response.statusCode());
//        System.out.println("Body   = " + response.body());

        if (response.statusCode() == 200) {
            JavaType type = mapper.getTypeFactory().constructParametricType(PageResponse.class, c);
            PageResponse<T> page = mapper.readValue(response.body(), type);
            return page.getContent();
        }
        throw new RuntimeException(mapper.readTree(response.body()).get("message").asText());
    }

    private <T> List<T> getResponseWithoutToken(String s, Class<T> c) throws IOException, InterruptedException {
        //For objects {}
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

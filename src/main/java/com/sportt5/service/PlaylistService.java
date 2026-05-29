package com.sportt5.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportt5.model.PageResponse;
import com.sportt5.model.Playlists;
import com.sportt5.model.Songs;

import java.io.IOException;
import java.util.List;

public class PlaylistService {

    private final ObjectMapper mapper;

    public PlaylistService() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.PUBLIC_ONLY);
        mapper.setVisibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.ANY);
    }

    public List<Playlists> getPublic() throws IOException, InterruptedException {
        String json = ConnectionServer.get("playlists");
        return parsePage(json, Playlists.class);
    }

    public List<Playlists> getMine() throws IOException, InterruptedException {
        String json = ConnectionServer.getAuth("playlists/me");
        return parsePage(json, Playlists.class);
    }

    public Playlists getById(int id) throws IOException, InterruptedException {
        String json = ConnectionServer.getAuth("playlists/" + id);
        return mapper.readValue(json, Playlists.class);
    }

    public List<Songs> getSongs(int playlistId) throws IOException, InterruptedException {
        String json = ConnectionServer.get("playlists/" + playlistId + "/songs");
        return parsePage(json, Songs.class);
    }

    public void addSong(int playlistId, int songId) throws IOException, InterruptedException {
        ConnectionServer.postAuth("playlists/" + playlistId + "/songs/" + songId, "");
    }

    public void removeSong(int playlistId, int songId) throws IOException, InterruptedException {
        ConnectionServer.deleteAuth("playlists/" + playlistId + "/songs/" + songId);
    }

    public void follow(int playlistId) throws IOException, InterruptedException {
        ConnectionServer.postAuth("playlists/" + playlistId + "/follow", "");
    }

    public void unfollow(int playlistId) throws IOException, InterruptedException {
        ConnectionServer.deleteAuth("playlists/" + playlistId + "/follow");
    }

    private <T> List<T> parsePage(String json, Class<T> type) throws IOException {
        PageResponse<T> page = mapper.readValue(json,
                mapper.getTypeFactory().constructParametricType(PageResponse.class, type));
        return page.getContent();
    }
}

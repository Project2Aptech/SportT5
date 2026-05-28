package com.sportt5.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class LikedSongService {

    private final ObjectMapper mapper = new ObjectMapper();

    public int getCount() throws IOException, InterruptedException {
        String json = ConnectionServer.getAuth("liked-songs?size=1");
        JsonNode root = mapper.readTree(json);
        return root.path("totalElements").asInt(0);
    }

    public boolean isLiked(int songId) throws IOException, InterruptedException {
        String json = ConnectionServer.getAuth("liked-songs/" + songId + "/status");
        return Boolean.parseBoolean(json.trim());
    }

    public void like(int songId) throws IOException, InterruptedException {
        ConnectionServer.postAuth("liked-songs/" + songId, "");
    }

    public void unlike(int songId) throws IOException, InterruptedException {
        ConnectionServer.deleteAuth("liked-songs/" + songId);
    }
}

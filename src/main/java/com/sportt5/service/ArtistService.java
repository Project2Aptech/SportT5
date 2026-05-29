package com.sportt5.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ArtistService {

    private final ObjectMapper mapper = new ObjectMapper();

    public long getFollowersCount(int artistId) throws IOException, InterruptedException {
        String json = ConnectionServer.get("artists/" + artistId + "/followers/count");
        return Long.parseLong(json.trim());
    }

    public boolean isFollowing(int artistId) throws IOException, InterruptedException {
        String json = ConnectionServer.getAuth("artists/" + artistId + "/following/status");
        return Boolean.parseBoolean(json.trim());
    }

    public void follow(int artistId) throws IOException, InterruptedException {
        ConnectionServer.postAuth("artists/" + artistId + "/follow", "");
    }

    public void unfollow(int artistId) throws IOException, InterruptedException {
        ConnectionServer.deleteAuth("artists/" + artistId + "/follow");
    }
}

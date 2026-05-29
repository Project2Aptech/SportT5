package com.sportt5.util;

import java.net.http.HttpClient;

public class ServerConnect {
    private final static HttpClient client;

    static  {
        client = HttpClient.newHttpClient();
    }

    public static HttpClient getClient() {
        return client;
    }
}

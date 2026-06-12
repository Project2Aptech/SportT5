package com.sportt5.session;

import java.nio.file.Files;
import java.nio.file.Path;

public class TokenStorage {
    private static final String FILE =
            System.getProperty("user.dir") + "/.sportt5_token";


    public static void saveToken(String token) {
        try {
            Files.writeString(Path.of(FILE), token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String loadToken() {
        try {
            return Files.readString(Path.of(FILE));
        } catch (Exception e) {
            return null;
        }
    }

    public static void clearToken() {
        try {
            Files.deleteIfExists(Path.of(FILE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

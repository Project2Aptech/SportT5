package com.sportt5.session;

import com.sportt5.model.Users;

import java.util.prefs.Preferences;

public final class UserSession {
    private static UserSession instance;
    private static final Preferences PREFS = Preferences.userNodeForPackage(UserSession.class);
    private static final String PREF_TOKEN_KEY = "authToken";

    private String token;
    private Users currentUser;

    private UserSession(String token, Users user) {
        this.token = token;
        this.currentUser = user;

        if (token != null) {
            PREFS.put(PREF_TOKEN_KEY, token);
        } else {
            PREFS.remove(PREF_TOKEN_KEY);
        }
    }

    public static void startSession(String token, Users user) {
        instance = new UserSession(token, user);
    }

    public static UserSession getInstance() {
        return instance;
    }

    public static void cleanSession() {
        instance = null;
        PREFS.remove(PREF_TOKEN_KEY);
    }

    public static void setCurrentUser(Users user) {
        if (instance == null) {
            return;
        } else {
            instance.currentUser = user;
        }
    }

    public String getToken() {
        return token;
    }

    public Users getCurrentUser() {
        return currentUser;
    }

    public int getCurrentUserId() {
        return (currentUser != null) ? currentUser.getId() : -1;
    }

//    public static String loadSavedToken() {
//        return PREFS.get(PREF_TOKEN_KEY, null);
//    }
}

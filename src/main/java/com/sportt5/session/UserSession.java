package com.sportt5.session;

import com.sportt5.model.Users;

public class UserSession {
    private static  UserSession instance;
    private String token;
    private Users currentUser;

    public UserSession( String token,Users currentUser) {
        this.currentUser = currentUser;
        this.token = token;
    }

    public static void startSession(String token, Users currentUser) {
        instance = new UserSession(token, currentUser);
    }
    public static UserSession getInstance() {
        return instance;
    }
    public static void cleanSession() {
        instance = null;
    }

    public String getToken() {
        return token;
    }

    public Users getCurrentUser() {
        return currentUser;
    }

}

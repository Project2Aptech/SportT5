package com.sportt5.model;

import com.sportt5.model.enums.AccountType;
import com.sportt5.model.enums.Roles;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Users {
    private int id;
    private String username;
    private String email;
    private String password_hash;

    private Roles role = Roles.USER;
    private AccountType accountType = AccountType.NORMAL;

    private String display_name;
    private String avatar_url;
    private String bio;

    private LocalDate birth_date;

    private boolean is_active = true;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public Users() {
    }

    public Users(int id, String username, String email, String password_hash, Roles role, AccountType accountType, String display_name, String avatar_url, String bio, LocalDate birth_date, boolean is_active) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password_hash = password_hash;
        this.role = role;
        this.accountType = accountType;
        this.display_name = display_name;
        this.avatar_url = avatar_url;
        this.bio = bio;
        this.birth_date = birth_date;
        this.is_active = is_active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }
}

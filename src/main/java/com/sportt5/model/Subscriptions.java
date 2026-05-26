package com.sportt5.model;

import com.sportt5.model.enums.AccountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Subscriptions {
    private int id;
    private int user_id;
    private AccountType plan_type;
    private BigDecimal amount;
    private LocalDateTime started_at;
    private LocalDateTime expires_at;
    private String status = "ACTIVE"; // ACTIVE | EXPIRED | CANCELLED
    private LocalDateTime created_at;

    public Subscriptions() {
    }

    public Subscriptions(BigDecimal amount, LocalDateTime expires_at, int id,
                         AccountType plan_type, LocalDateTime started_at,
                         String status, int user_id) {
        this.amount = amount;
        this.expires_at = expires_at;
        this.id = id;
        this.plan_type = plan_type;
        this.started_at = started_at;
        this.status = status;
        this.user_id = user_id;
    }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }

    public LocalDateTime getExpires_at() { return expires_at; }
    public void setExpires_at(LocalDateTime expires_at) { this.expires_at = expires_at; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public AccountType getPlan_type() { return plan_type; }
    public void setPlan_type(AccountType plan_type) { this.plan_type = plan_type; }

    public LocalDateTime getStarted_at() { return started_at; }
    public void setStarted_at(LocalDateTime started_at) { this.started_at = started_at; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getUser_id() { return user_id; }
    public void setUser_id(int user_id) { this.user_id = user_id; }
}

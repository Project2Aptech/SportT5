package com.sportt5.model;

import com.sportt5.model.enums.AccountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Subscriptions {
    private int id;
    private int userId;
    private AccountType planType;
    private BigDecimal amount;
    private LocalDateTime startedAt;
    private LocalDateTime expiresAt;
    private String status = "ACTIVE"; // ACTIVE | EXPIRED | CANCELLED
    private LocalDateTime createdAt;

    public Subscriptions() {
    }

    public Subscriptions(BigDecimal amount, LocalDateTime expiresAt, int id,
                         AccountType planType, LocalDateTime startedAt,
                         String status, int userId) {
        this.amount = amount;
        this.expiresAt = expiresAt;
        this.id = id;
        this.planType = planType;
        this.startedAt = startedAt;
        this.status = status;
        this.userId = userId;
    }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public AccountType getPlanType() { return planType; }
    public void setPlanType(AccountType planType) { this.planType = planType; }

    public LocalDateTime getStartedAt() { return startedAt; }
    public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}

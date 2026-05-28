package com.sportt5.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ArtistEarnings {
    private int id;
    private int artistId;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private int streamCount;
    private BigDecimal amount;
    private LocalDateTime createdAt;

    public ArtistEarnings() {
    }

    public ArtistEarnings(BigDecimal amount, int artistId, int id,
                          LocalDate periodEnd, LocalDate periodStart,
                          int streamCount) {
        this.amount = amount;
        this.artistId = artistId;
        this.id = id;
        this.periodEnd = periodEnd;
        this.periodStart = periodStart;
        this.streamCount = streamCount;
    }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public int getArtistId() { return artistId; }
    public void setArtistId(int artistId) { this.artistId = artistId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getPeriodEnd() { return periodEnd; }
    public void setPeriodEnd(LocalDate periodEnd) { this.periodEnd = periodEnd; }

    public LocalDate getPeriodStart() { return periodStart; }
    public void setPeriodStart(LocalDate periodStart) { this.periodStart = periodStart; }

    public int getStreamCount() { return streamCount; }
    public void setStreamCount(int streamCount) { this.streamCount = streamCount; }
}

package com.sportt5.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ArtistEarnings {
    private int id;
    private int artist_id;
    private LocalDate period_start;
    private LocalDate period_end;
    private int stream_count;
    private BigDecimal amount;
    private LocalDateTime created_at;

    public ArtistEarnings() {
    }

    public ArtistEarnings(BigDecimal amount, int artist_id, int id,
                          LocalDate period_end, LocalDate period_start,
                          int stream_count) {
        this.amount = amount;
        this.artist_id = artist_id;
        this.id = id;
        this.period_end = period_end;
        this.period_start = period_start;
        this.stream_count = stream_count;
    }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public int getArtist_id() { return artist_id; }
    public void setArtist_id(int artist_id) { this.artist_id = artist_id; }

    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getPeriod_end() { return period_end; }
    public void setPeriod_end(LocalDate period_end) { this.period_end = period_end; }

    public LocalDate getPeriod_start() { return period_start; }
    public void setPeriod_start(LocalDate period_start) { this.period_start = period_start; }

    public int getStream_count() { return stream_count; }
    public void setStream_count(int stream_count) { this.stream_count = stream_count; }
}

package com.lmajstorovic.agencyportal.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    private UUID id = UUID.randomUUID();
    private String username;
    private String password;
    private String rank;
    @Nullable
    private String tag;
    @Nullable
    @Column(name = "id_personal_secretary")
    private UUID idPersonalSecretary;
    private Boolean approved;
    @Column(name = "created_at")
    private Timestamp createdAt = Timestamp.from(Instant.now());

    public User(String username, String password, String rank, Boolean approved) {
        this.username = username;
        this.password = password;
        this.rank = rank;
        this.approved = approved;
    }

    public User() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Nullable
    public String getTag() {
        return tag;
    }

    public void setTag(@Nullable String tag) {
        this.tag = tag;
    }

    @Nullable
    public UUID getIdPersonalSecretary() {
        return idPersonalSecretary;
    }

    public void setIdPersonalSecretary(@Nullable UUID idPersonalSecretary) {
        this.idPersonalSecretary = idPersonalSecretary;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}

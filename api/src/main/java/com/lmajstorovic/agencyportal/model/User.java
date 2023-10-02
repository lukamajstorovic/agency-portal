package com.lmajstorovic.agencyportal.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Getter
    @Id
    private UUID id = UUID.randomUUID();
    @Getter
    private String username;
    @Getter
    private String password;
    @Getter
    private String rank = "Agent I";
    @Nullable
    private String tag;
    @Nullable
    @Column(name = "id_personal_secretary")
    private UUID idPersonalSecretary;
    @Getter
    private Boolean approved = false;
    @Getter
    @Column(name = "created_at")
    private Timestamp createdAt = Timestamp.from(Instant.now());

    public User(String username, String password, String rank) {
        this.username = username;
        this.password = password;
        this.rank = rank;
    }

    public User(UUID id, String username, String password, String rank, @Nullable String tag, @Nullable UUID idPersonalSecretary, Boolean approved) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rank = rank;
        this.tag = tag;
        this.idPersonalSecretary = idPersonalSecretary;
        this.approved = approved;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}

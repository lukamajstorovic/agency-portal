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
import java.util.Collection;
import java.util.UUID;


@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User implements UserDetails {

    @Id
    private UUID id = UUID.randomUUID();
    private String username;
    private String password;
    private UUID idRank;
    @Nullable
    private String tag;
    @Nullable
    @Column(name = "id_personal_secretary")
    private UUID idPersonalSecretary;
    private Boolean approved = false;
    @Column(name = "created_at")
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    public User(String username, String password, UUID idRank) {
        this.username = username;
        this.password = password;
        this.idRank = idRank;
    }

    public User(UUID id, String username, String password, UUID idRank, @Nullable String tag, @Nullable UUID idPersonalSecretary, Boolean approved) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.idRank = idRank;
        this.tag = tag;
        this.idPersonalSecretary = idPersonalSecretary;
        this.approved = approved;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}

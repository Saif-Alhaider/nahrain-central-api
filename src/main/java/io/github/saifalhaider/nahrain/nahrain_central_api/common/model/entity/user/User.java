package io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "_user")
public abstract class User implements UserDetails {
    @Id
    @GeneratedValue
    private Integer id;
    private String fullName;
    private String email;
    private String password;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean mfaEnabled;
    private String totpSecret;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<? extends GrantedAuthority> authorities;
        if (role == null) {
            authorities = List.of();
        } else {
            authorities = List.of(new SimpleGrantedAuthority(role.name()));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @PrePersist
    public void assignSubclass() {
        if (this instanceof Prof) {
            this.role = Role.PROF;
        } else if (this instanceof Student) {
            this.role = Role.STUDENT;
        } else if (this instanceof Admin) {
            this.role = Role.ADMIN;
        } else {
            this.role = null;
        }
    }

    public enum Role {
        ADMIN,
        PROF,
        STUDENT
    }
}
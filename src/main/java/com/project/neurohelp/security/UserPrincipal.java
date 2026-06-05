package com.project.neurohelp.security;

import com.project.neurohelp.platform.constants.Roles;
import com.project.neurohelp.platform.constants.Status;
import com.project.neurohelp.repositories.user.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class UserPrincipal implements UserDetails {
    private final UUID id;
    private final String email;
    private final String password;
    private final Status status;
    private final Collection<? extends GrantedAuthority> authorities;

    private UserPrincipal(UUID id, String email, String password, Status status,
                          Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.status = status;
        this.authorities = authorities;
    }

    public static UserPrincipal from(UserEntity user) {
        Roles role = user.getRoles() == null ? Roles.USER : user.getRoles();
        Status status = user.getStatus() == null ? Status.INACTIVE : user.getStatus();
        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                status,
                List.of(new SimpleGrantedAuthority("ROLE_" + role.name()))
        );
    }

    public UUID getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
        return status == Status.ACTIVE;
    }
}


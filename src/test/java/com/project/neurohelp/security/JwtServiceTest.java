package com.project.neurohelp.security;

import com.project.neurohelp.platform.constants.Roles;
import com.project.neurohelp.repositories.user.UserEntity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class JwtServiceTest {
    @Test
    void generatesAndValidatesToken() {
        JwtProperties properties = new JwtProperties(
                "01234567890123456789012345678901",
                "neurohelp",
                5
        );
        JwtService jwtService = new JwtService(properties);

        UserEntity user = new UserEntity();
        user.setId(UUID.randomUUID());
        user.setEmail("user@example.com");
        user.setRoles(Roles.USER);

        String token = jwtService.generateAccessToken(user);

        assertThat(jwtService.isTokenValid(token)).isTrue();
        assertThat(jwtService.extractSubject(token)).isEqualTo("user@example.com");
    }
}


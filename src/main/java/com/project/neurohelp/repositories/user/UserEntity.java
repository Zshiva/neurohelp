package com.project.neurohelp.repositories.user;

import com.project.neurohelp.platform.constants.Roles;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class UserEntity {

    private String id;
    private String username;
    private String email;
    @Enumerated(EnumType.STRING)
    private Roles roles;
}

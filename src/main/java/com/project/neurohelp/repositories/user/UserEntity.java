package com.project.neurohelp.repositories.user;

public class UserEntity {

    private String id;
    private String username;
    private String email;
    @Enumerated(EnumType.STRING)
    private Roles roles;
}

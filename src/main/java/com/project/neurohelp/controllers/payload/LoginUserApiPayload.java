package com.project.neurohelp.controllers.payload;

public record LoginUserApiPayload(
        String username,
        String userPassword
) {
}

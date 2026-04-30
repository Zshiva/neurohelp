package com.project.neurohelp.controllers.payload.login;

import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder

public record LoginUserApiPayload(
        String username,
        String userPassword
) {
}

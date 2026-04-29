package com.project.neurohelp.controllers.payload;

import io.soabase.recordbuilder.core.RecordBuilder;
import org.springframework.context.annotation.Bean;

@RecordBuilder

public record LoginUserApiPayload(
        String username,
        String userPassword
) {
}

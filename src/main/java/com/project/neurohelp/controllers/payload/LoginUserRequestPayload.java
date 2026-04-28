package com.project.neurohelp.controllers.payload;

import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record LoginUserRequestPayload(
        String emailId,
        String password
) { }
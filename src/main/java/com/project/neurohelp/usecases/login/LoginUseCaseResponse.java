package com.project.neurohelp.usecases.login;

import com.project.neurohelp.platform.usecase.UseCaseResponse;
import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record LoginUseCaseResponse(
        String message,
        String accessToken,
        String tokenType,
        long expiresInSeconds
) implements UseCaseResponse {
}

package com.project.neurohelp.usecases.passwordreset.request;

import com.project.neurohelp.platform.usecase.UseCaseResponse;

public record PasswordResetRequestUseCaseResponse(String message) implements UseCaseResponse {
}


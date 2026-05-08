package com.project.neurohelp.usecases.passwordreset.request;

import com.project.neurohelp.platform.usecase.UseCaseRequest;
import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record PasswordResetRequestUseCaseRequest(String email) implements UseCaseRequest {
}


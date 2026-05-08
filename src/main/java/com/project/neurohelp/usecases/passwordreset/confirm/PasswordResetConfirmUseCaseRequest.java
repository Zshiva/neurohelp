package com.project.neurohelp.usecases.passwordreset.confirm;

import com.project.neurohelp.platform.usecase.UseCaseRequest;
import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record PasswordResetConfirmUseCaseRequest(String email, String otp, String newPassword) implements UseCaseRequest {
}


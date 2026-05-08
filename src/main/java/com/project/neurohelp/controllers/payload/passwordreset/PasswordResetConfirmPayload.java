package com.project.neurohelp.controllers.payload.passwordreset;

public record PasswordResetConfirmPayload(String email, String otp, String newPassword) {
}


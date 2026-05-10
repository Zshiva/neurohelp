package com.project.neurohelp.usecases.passwordreset.confirm;

import com.project.neurohelp.platform.exception.NeuroHelpErrorMessage;
import com.project.neurohelp.platform.exception.NeuroHelpException;
import com.project.neurohelp.platform.usecase.UseCase;
import com.project.neurohelp.platform.utils.helperutils.OtpUtil;
import com.project.neurohelp.repositories.user.UserEntity;
import com.project.neurohelp.repositories.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class PasswordResetConfirmUseCase implements UseCase<PasswordResetConfirmUseCaseRequest, PasswordResetConfirmUseCaseResponse> {

    private final UserRepository userRepository;

    public PasswordResetConfirmUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<PasswordResetConfirmUseCaseResponse> execute(PasswordResetConfirmUseCaseRequest request) {
        validate(request);

        UserEntity user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new NeuroHelpException(NeuroHelpErrorMessage.USER_NOT_FOUND));

        if (user.getPasswordResetOtpHash() == null || user.getPasswordResetOtpExpiresAt() == null) {
            throw new NeuroHelpException(NeuroHelpErrorMessage.OTP_INVALID_OR_EXPIRED);
        }

        if (user.getPasswordResetOtpExpiresAt().isBefore(Instant.now())) {
            throw new NeuroHelpException(NeuroHelpErrorMessage.OTP_INVALID_OR_EXPIRED);
        }

        String expectedHash = user.getPasswordResetOtpHash();
        String providedHash = OtpUtil.sha256WithSalt(request.otp(), user.getId().toString());
        if (!expectedHash.equals(providedHash)) {
            throw new NeuroHelpException(NeuroHelpErrorMessage.OTP_INVALID_OR_EXPIRED);
        }

        // Business rule: do not allow setting the same password as current one.
        // (Currently passwords are stored in plaintext in this project.)
        if (user.getPassword() != null && user.getPassword().equals(request.newPassword())) {
            throw new NeuroHelpException(NeuroHelpErrorMessage.NEW_PASSWORD_MUST_BE_DIFFERENT);
        }

        user.setPassword(request.newPassword());
        user.setPasswordResetOtpHash(null);
        user.setPasswordResetOtpExpiresAt(null);
        userRepository.save(user);

        return Optional.of(new PasswordResetConfirmUseCaseResponse("Password reset successful"));
    }

    private void validate(PasswordResetConfirmUseCaseRequest request) {
        if (request == null || request.email() == null || request.email().isBlank()) {
            throw new NeuroHelpException(NeuroHelpErrorMessage.EMAIL_ID_REQUIRED);
        }
        if (request.otp() == null || request.otp().isBlank()) {
            throw new NeuroHelpException(NeuroHelpErrorMessage.OTP_IS_REQUIRED);
        }
        if (request.newPassword() == null || request.newPassword().isBlank()) {
            throw new NeuroHelpException(NeuroHelpErrorMessage.NEW_PASSWORD_REQUIRED);
        }
    }
}


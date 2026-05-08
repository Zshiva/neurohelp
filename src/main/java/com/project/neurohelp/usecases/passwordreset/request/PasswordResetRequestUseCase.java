package com.project.neurohelp.usecases.passwordreset.request;

import com.project.neurohelp.platform.exception.NeuroHelpErrorMessage;
import com.project.neurohelp.platform.exception.NeuroHelpException;
import com.project.neurohelp.platform.usecase.UseCase;
import com.project.neurohelp.platform.utils.helperutils.OtpUtil;
import com.project.neurohelp.repositories.user.UserEntity;
import com.project.neurohelp.repositories.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Service
public class PasswordResetRequestUseCase implements UseCase<PasswordResetRequestUseCaseRequest, PasswordResetRequestUseCaseResponse> {
    private static final Duration OTP_TTL = Duration.ofMinutes(10);

    private final UserRepository userRepository;

    public PasswordResetRequestUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<PasswordResetRequestUseCaseResponse> execute(PasswordResetRequestUseCaseRequest request) {
        validate(request);

        UserEntity user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new NeuroHelpException(NeuroHelpErrorMessage.USER_NOT_FOUND));

        // NOTE: in a real system you would send this OTP via email/SMS.
        // For learning/dev we generate + store only hashed OTP.
        String otp = OtpUtil.generateNumericOtp(6);
        String otpHash = OtpUtil.sha256WithSalt(otp, user.getId().toString());

        user.setPasswordResetOtpHash(otpHash);
        user.setPasswordResetOtpExpiresAt(Instant.now().plus(OTP_TTL));
        userRepository.save(user);

        // Dev-friendly response: returning OTP string helps you test quickly.
        // For production, DO NOT return OTP in API response.
        return Optional.of(new PasswordResetRequestUseCaseResponse("Password reset OTP generated: " + otp));
    }

    private void validate(PasswordResetRequestUseCaseRequest request) {
        if (request == null || request.email() == null || request.email().isBlank()) {
            throw new NeuroHelpException(NeuroHelpErrorMessage.EMAIL_ID_REQUIRED);
        }
    }
}


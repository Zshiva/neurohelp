package com.project.neurohelp.usecases.login;

import com.project.neurohelp.platform.exception.NeuroHelpErrorMessage;
import com.project.neurohelp.platform.exception.NeuroHelpException;
import com.project.neurohelp.platform.usecase.UseCase;
import com.project.neurohelp.repositories.user.UserEntity;
import com.project.neurohelp.repositories.user.UserRepository;
import com.project.neurohelp.security.JwtService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginUseCase implements UseCase<LoginUseCaseRequest, LoginUseCaseResponse> {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public LoginUseCase(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public Optional<LoginUseCaseResponse> execute(LoginUseCaseRequest request) {
        UserEntity user = validateLogin(request);
        String token = jwtService.generateAccessToken(user);
        return Optional.of(new LoginUseCaseResponse(
                "Login successful",
                token,
                "Bearer",
                jwtService.getAccessTokenExpiresInSeconds()
        ));
    }
    private UserEntity validateLogin(LoginUseCaseRequest request) {
        String email = request.email();
        String password = request.password();

        if (email == null || email.isBlank()) {
            throw new NeuroHelpException(NeuroHelpErrorMessage.EMAIL_ID_REQUIRED);
        }
        if (password == null || password.isBlank()) {
            throw new NeuroHelpException(NeuroHelpErrorMessage.PASSWORD_IS_REQUIRED);
        }
        Optional<UserEntity> userDetails = userRepository.findByEmail(email);
        if (userDetails.isEmpty()) {
            throw new NeuroHelpException(NeuroHelpErrorMessage.LOGIN_CREDENTIALS_DOES_NOT_MATCH);
        }
        UserEntity userEntity = userDetails.get();
        if (!userEntity.getPassword().equals(password)) {
            throw new NeuroHelpException(NeuroHelpErrorMessage.LOGIN_CREDENTIALS_DOES_NOT_MATCH);
        }
        return userEntity;
    }
}

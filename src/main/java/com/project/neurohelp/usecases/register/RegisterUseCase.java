package com.project.neurohelp.usecases.register;

import com.project.neurohelp.controllers.converter.UserConverter;
import com.project.neurohelp.platform.exception.NeuroHelpErrorMessage;
import com.project.neurohelp.platform.exception.NeuroHelpException;
import com.project.neurohelp.platform.usecase.UseCase;
import com.project.neurohelp.platform.utils.helperutils.PasswordGenerator;
import com.project.neurohelp.repositories.user.UserEntity;
import com.project.neurohelp.repositories.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterUseCase implements UseCase<RegisterUseCaseRequest, RegisterUseCaseResponse> {
    private final UserRepository userRepository;
    private final PasswordGenerator passwordGenerator;

    public RegisterUseCase(UserRepository userRepository, PasswordGenerator passwordGenerator) {
        this.userRepository = userRepository;
        this.passwordGenerator = passwordGenerator;
    }

    @Override
    public Optional<RegisterUseCaseResponse> execute(RegisterUseCaseRequest request) {
        validateRegistration(request);
        String password = passwordGenerator.generateRandomPassword(8);
        UserEntity user = UserConverter.toEntity(request, password);
        UserEntity savedEntity = userRepository.save(user);
        return Optional.of(new RegisterUseCaseResponse(savedEntity.getEmail(), savedEntity.getStatus(), "Registration Successful"));
    }

    private void validateRegistration(RegisterUseCaseRequest userRegisterRequest) {
        String email = userRegisterRequest.email();
        String citizenShipRecord = userRegisterRequest.citizenShip();
        if (email == null || email.isBlank()) {
            throw new NeuroHelpException(NeuroHelpErrorMessage.EMAIL_ID_REQUIRED);
        }
        if(citizenShipRecord.isBlank() || citizenShipRecord == null){
            throw new NeuroHelpException(NeuroHelpErrorMessage.CITIZENSHIP_RECORD_IS_REQUIRED);
        }
        if(userRepository.findByEmail(email).isPresent()){
            throw new NeuroHelpException(NeuroHelpErrorMessage.EMAIL_ID_ALREADY_EXISTS);
        }
        if(userRepository.findByCitizenShip(citizenShipRecord).isPresent()){
            throw new NeuroHelpException(NeuroHelpErrorMessage.CITIZENSHIP_RECORD_ALREADY_EXISTS);
        }
    }
}

package com.project.neurohelp.controllers.converter;

import com.project.neurohelp.controllers.payload.login.LoginUserRequestPayload;
import com.project.neurohelp.controllers.payload.passwordreset.PasswordResetConfirmPayload;
import com.project.neurohelp.controllers.payload.passwordreset.PasswordResetRequestPayload;
import com.project.neurohelp.controllers.payload.register.RegisterUserRequestPayload;
import com.project.neurohelp.platform.constants.Status;
import com.project.neurohelp.repositories.user.UserEntity;
import com.project.neurohelp.usecases.login.LoginUseCaseRequest;
import com.project.neurohelp.usecases.login.LoginUseCaseRequestBuilder;
import com.project.neurohelp.usecases.passwordreset.confirm.PasswordResetConfirmUseCaseRequest;
import com.project.neurohelp.usecases.passwordreset.confirm.PasswordResetConfirmUseCaseRequestBuilder;
import com.project.neurohelp.usecases.passwordreset.request.PasswordResetRequestUseCaseRequest;
import com.project.neurohelp.usecases.passwordreset.request.PasswordResetRequestUseCaseRequestBuilder;
import com.project.neurohelp.usecases.register.RegisterUseCaseRequest;
import com.project.neurohelp.usecases.register.RegisterUseCaseRequestBuilder;

import java.util.UUID;

public class UserConverter {
    public static LoginUseCaseRequest toRequest(LoginUserRequestPayload payload){
        return LoginUseCaseRequestBuilder.builder()
                .email(payload.email())
                .password(payload.password())
                .build();
    }
    public static RegisterUseCaseRequest toRegisterRequest(RegisterUserRequestPayload payload){
        return RegisterUseCaseRequestBuilder.builder()
                .name(payload.name())
                .email(payload.email())
                .address(payload.address())
                .citizenShip(payload.citizenShip())
                .roles(payload.roles())
                .status(payload.status())
                .build();
    }
    public static UserEntity toEntity(RegisterUseCaseRequest request, String password){
        UserEntity userEntity = new UserEntity();
        userEntity.setName(request.name());
        userEntity.setEmail(request.email());
        userEntity.setPassword(password);
        userEntity.setAddress(request.address());
        userEntity.setRoles(request.roles());
        userEntity.setCitizenShip(request.citizenShip());
        userEntity.setStatus(Status.INACTIVE);
        return userEntity;
    }

    public static PasswordResetRequestUseCaseRequest toPasswordResetRequest(PasswordResetRequestPayload payload) {
        return PasswordResetRequestUseCaseRequestBuilder.builder()
                .email(payload.email())
                .build();
    }

    public static PasswordResetConfirmUseCaseRequest toPasswordResetConfirmRequest(PasswordResetConfirmPayload payload) {
        return PasswordResetConfirmUseCaseRequestBuilder.builder()
                .email(payload.email())
                .otp(payload.otp())
                .newPassword(payload.newPassword())
                .build();
    }
}

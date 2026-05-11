package com.project.neurohelp.controllers;

import com.project.neurohelp.controllers.converter.UserConverter;
import com.project.neurohelp.controllers.payload.login.LoginUserRequestPayload;
import com.project.neurohelp.controllers.payload.passwordreset.PasswordResetConfirmPayload;
import com.project.neurohelp.controllers.payload.passwordreset.PasswordResetRequestPayload;
import com.project.neurohelp.controllers.payload.register.RegisterUserRequestPayload;
import com.project.neurohelp.platform.exception.NeuroHelpErrorMessage;
import com.project.neurohelp.platform.exception.NeuroHelpException;
import com.project.neurohelp.platform.rest.RestResponse;
import com.project.neurohelp.usecases.login.LoginUseCase;
import com.project.neurohelp.usecases.login.LoginUseCaseRequest;
import com.project.neurohelp.usecases.passwordreset.confirm.PasswordResetConfirmUseCase;
import com.project.neurohelp.usecases.passwordreset.confirm.PasswordResetConfirmUseCaseRequest;
import com.project.neurohelp.usecases.passwordreset.request.PasswordResetRequestUseCase;
import com.project.neurohelp.usecases.passwordreset.request.PasswordResetRequestUseCaseRequest;
import com.project.neurohelp.usecases.register.RegisterUseCase;
import com.project.neurohelp.usecases.register.RegisterUseCaseRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final LoginUseCase loginUseCase;
    private final RegisterUseCase registerUseCase;
    private final PasswordResetRequestUseCase passwordResetRequestUseCase;
    private final PasswordResetConfirmUseCase passwordResetConfirmUseCase;

    public UserController(
            LoginUseCase loginUseCase,
            RegisterUseCase registerUseCase,
            PasswordResetRequestUseCase passwordResetRequestUseCase,
            PasswordResetConfirmUseCase passwordResetConfirmUseCase
    ){
        this.loginUseCase = loginUseCase;
        this.registerUseCase = registerUseCase;
        this.passwordResetRequestUseCase = passwordResetRequestUseCase;
        this.passwordResetConfirmUseCase = passwordResetConfirmUseCase;
    }

    @PostMapping("/neurohelp/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginUserRequestPayload payload){
        LoginUseCaseRequest request = UserConverter.toRequest(payload);
        var response = loginUseCase.execute(request);
        if(response.isPresent()) {
            return ResponseEntity.ok(RestResponse.success(response.get()));
        }else {
            throw new NeuroHelpException(NeuroHelpErrorMessage.LOGIN_UNSUCCESSFUL);
        }
    }

    @PostMapping("/neurohelp/register")
    public ResponseEntity<Object> registerUser(@RequestBody RegisterUserRequestPayload payload) {
        RegisterUseCaseRequest registerRequest = UserConverter.toRegisterRequest(payload);
        var response = registerUseCase.execute(registerRequest);
        if(response.isPresent()) {
            return ResponseEntity.ok(RestResponse.success(response.get()));
        }else {
            throw new NeuroHelpException(NeuroHelpErrorMessage.REGISTER_UNSUCCESSFUL);
        }
    }

    @PostMapping("/neurohelp/password-reset/request")
    public ResponseEntity<Object> passwordResetRequest(@RequestBody PasswordResetRequestPayload payload) {
        PasswordResetRequestUseCaseRequest request = UserConverter.toPasswordResetRequest(payload);
        var response = passwordResetRequestUseCase.execute(request);
        if (response.isPresent()) {
            return ResponseEntity.ok(RestResponse.success(response.get()));
        }
        return ResponseEntity.ok(RestResponse.success());
    }

    @PostMapping("/neurohelp/password-reset/confirm")
    public ResponseEntity<Object> passwordResetConfirm(@RequestBody PasswordResetConfirmPayload payload) {
        PasswordResetConfirmUseCaseRequest request = UserConverter.toPasswordResetConfirmRequest(payload);
        var response = passwordResetConfirmUseCase.execute(request);
        if (response.isPresent()) {
            return ResponseEntity.ok(RestResponse.success(response.get()));
        }
        return ResponseEntity.ok(RestResponse.success());
    }

}

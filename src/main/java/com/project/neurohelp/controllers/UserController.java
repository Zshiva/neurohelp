package com.project.neurohelp.controllers;

import com.project.neurohelp.controllers.converter.UserConverter;
import com.project.neurohelp.controllers.payload.login.LoginUserRequestPayload;
import com.project.neurohelp.controllers.payload.register.RegisterUserRequestPayload;
import com.project.neurohelp.platform.exception.NeuroHelpErrorMessage;
import com.project.neurohelp.platform.exception.NeuroHelpException;
import com.project.neurohelp.platform.rest.RestResponse;
import com.project.neurohelp.usecases.login.LoginUseCase;
import com.project.neurohelp.usecases.login.LoginUseCaseRequest;
import com.project.neurohelp.usecases.register.RegisterUseCase;
import com.project.neurohelp.usecases.register.RegisterUseCaseRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class UserController {
    private final LoginUseCase loginUseCase;
    private final RegisterUseCase registerUseCase;
    public UserController(LoginUseCase loginUseCase, RegisterUseCase registerUseCase){
        this.loginUseCase = loginUseCase;
        this.registerUseCase = registerUseCase;
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
        // Call the register use case and handle the response accordingly
        // For example:
        // var registerResponse = registerUseCase.execute(registerRequest);
    }

}

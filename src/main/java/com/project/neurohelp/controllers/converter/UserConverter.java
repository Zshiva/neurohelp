package com.project.neurohelp.controllers.converter;

import com.project.neurohelp.controllers.payload.LoginUserRequestPayload;
import com.project.neurohelp.usecases.login.LoginUseCaseRequest;
import com.project.neurohelp.usecases.login.LoginUseCaseRequestBuilder;

public class UserConverter {
    public static LoginUseCaseRequest toRequest(LoginUserRequestPayload payload){
        return LoginUseCaseRequestBuilder.builder()
                .emailId(payload.emailId())
                .password(payload.password())
                .build();
    }
}

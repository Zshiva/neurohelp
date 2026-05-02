package com.project.neurohelp.platform.usecase;

import com.project.neurohelp.usecases.login.LoginUseCaseRequest;
import com.project.neurohelp.usecases.login.LoginUseCaseResponse;

import java.io.IOException;
import java.util.Optional;

@FunctionalInterface
public interface UseCase <I extends UseCaseRequest, O extends UseCaseResponse>{
    Optional<O> execute(I request) throws IOException, InterruptedException;
}

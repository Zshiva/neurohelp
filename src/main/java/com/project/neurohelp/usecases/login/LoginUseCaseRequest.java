package com.project.neurohelp.usecases.login;

import com.project.neurohelp.platform.usecase.UseCaseRequest;
import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record LoginUseCaseRequest(
        String email,
        String password
) implements UseCaseRequest {
}

package com.project.neurohelp.usecases.register;

import com.project.neurohelp.platform.constants.Status;
import com.project.neurohelp.platform.usecase.UseCaseResponse;
import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@RecordBuilder
public record RegisterUseCaseResponse(
        String email,
        @Enumerated(EnumType.STRING)
        Status status,
        String message
) implements UseCaseResponse {
}

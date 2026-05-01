package com.project.neurohelp.usecases.register;

import com.project.neurohelp.platform.constants.Roles;
import com.project.neurohelp.platform.constants.Status;
import com.project.neurohelp.platform.usecase.UseCaseRequest;
import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@RecordBuilder
public record RegisterUseCaseRequest(
        String name,
        String email,
        String address,
        String citizenShip,
        @Enumerated(EnumType.STRING)
        Roles roles,
        @Enumerated(EnumType.STRING)
        Status status
) implements UseCaseRequest {
}

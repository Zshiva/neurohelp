package com.project.neurohelp.usecases.wellness.resources;

import com.project.neurohelp.platform.usecase.UseCaseRequest;
import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record CrisisResourcesUseCaseRequest(
        String country,
        String locale
) implements UseCaseRequest {
}


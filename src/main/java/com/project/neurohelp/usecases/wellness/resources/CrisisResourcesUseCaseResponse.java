package com.project.neurohelp.usecases.wellness.resources;

import com.project.neurohelp.platform.usecase.UseCaseResponse;
import io.soabase.recordbuilder.core.RecordBuilder;

import java.util.List;

@RecordBuilder
public record CrisisResourcesUseCaseResponse(
        String country,
        String locale,
        String title,
        String message,
        List<CrisisResource> resources,
        List<String> immediateSteps
) implements UseCaseResponse {

    @RecordBuilder
    public record CrisisResource(
            String name,
            String contact,
            String availability,
            String notes
    ) {
    }
}


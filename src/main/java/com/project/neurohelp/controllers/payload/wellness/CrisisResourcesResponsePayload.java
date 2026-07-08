package com.project.neurohelp.controllers.payload.wellness;

import io.soabase.recordbuilder.core.RecordBuilder;

import java.util.List;

@RecordBuilder
public record CrisisResourcesResponsePayload(
        String country,
        String locale,
        String title,
        String message,
        List<CrisisResourcePayload> resources,
        List<String> immediateSteps
) {

    @RecordBuilder
    public record CrisisResourcePayload(
            String name,
            String contact,
            String availability,
            String notes
    ) {
    }
}


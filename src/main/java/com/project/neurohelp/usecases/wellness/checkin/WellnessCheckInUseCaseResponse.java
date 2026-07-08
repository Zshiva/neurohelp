package com.project.neurohelp.usecases.wellness.checkin;

import com.project.neurohelp.platform.usecase.UseCaseResponse;
import io.soabase.recordbuilder.core.RecordBuilder;

import java.util.List;

@RecordBuilder
public record WellnessCheckInUseCaseResponse(
        String sessionId,
        String riskLevel,
        String summary,
        List<String> recommendedActions,
        List<String> groundingExercises,
        List<CrisisResource> crisisResources,
        String followUpPrompt
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


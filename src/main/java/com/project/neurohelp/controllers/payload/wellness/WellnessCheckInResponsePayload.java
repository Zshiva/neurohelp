package com.project.neurohelp.controllers.payload.wellness;

import io.soabase.recordbuilder.core.RecordBuilder;

import java.util.List;

@RecordBuilder
public record WellnessCheckInResponsePayload(
        String sessionId,
        String riskLevel,
        String summary,
        List<String> recommendedActions,
        List<String> groundingExercises,
        List<CrisisResourcePayload> crisisResources,
        String followUpPrompt
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


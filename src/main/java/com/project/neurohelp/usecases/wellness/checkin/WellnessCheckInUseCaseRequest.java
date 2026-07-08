package com.project.neurohelp.usecases.wellness.checkin;

import com.project.neurohelp.platform.usecase.UseCaseRequest;
import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record WellnessCheckInUseCaseRequest(
        String sessionId,
        Integer moodScore,
        Integer stressScore,
        Double sleepHours,
        Integer energyScore,
        String primaryFeeling,
        String notes,
        String locale,
        String country
) implements UseCaseRequest {
}


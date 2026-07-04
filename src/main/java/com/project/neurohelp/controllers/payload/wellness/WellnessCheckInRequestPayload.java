package com.project.neurohelp.controllers.payload.wellness;

import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@RecordBuilder
public record WellnessCheckInRequestPayload(
        @NotBlank(message = "Session ID is required")
        String sessionId,

        @NotNull(message = "Mood score is required")
        @Min(value = 1, message = "Mood score must be between 1 and 10")
        @Max(value = 10, message = "Mood score must be between 1 and 10")
        Integer moodScore,

        @NotNull(message = "Stress score is required")
        @Min(value = 1, message = "Stress score must be between 1 and 10")
        @Max(value = 10, message = "Stress score must be between 1 and 10")
        Integer stressScore,

        @NotNull(message = "Sleep hours is required")
        @Min(value = 0, message = "Sleep hours must be between 0 and 24")
        @Max(value = 24, message = "Sleep hours must be between 0 and 24")
        Double sleepHours,

        @NotNull(message = "Energy score is required")
        @Min(value = 1, message = "Energy score must be between 1 and 10")
        @Max(value = 10, message = "Energy score must be between 1 and 10")
        Integer energyScore,

        String primaryFeeling,
        String notes,
        String locale,
        String country
) {
}


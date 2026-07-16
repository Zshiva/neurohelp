package com.project.neurohelp.controllers.payload.wellness;

import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@RecordBuilder
public record JournalInsightRequestPayload(
        @NotBlank(message = "Session ID is required")
        String sessionId,

        @NotBlank(message = "Journal entry is required")
        @Size(max = 2000, message = "Journal entry must not exceed 2000 characters")
        String journalEntry,

        Integer moodScore,
        String locale,
        String country
) {
}


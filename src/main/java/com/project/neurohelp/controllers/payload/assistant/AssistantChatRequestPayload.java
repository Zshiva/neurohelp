package com.project.neurohelp.controllers.payload.assistant;

import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@RecordBuilder
public record AssistantChatRequestPayload(
		@NotBlank(message = "Session ID is required")
		String sessionId,

		@NotBlank(message = "User message is required")
		@Size(max = 4000, message = "User message must not exceed 4000 characters")
		String userMessage,

		String locale,
		String country,
		String mode
) {
}

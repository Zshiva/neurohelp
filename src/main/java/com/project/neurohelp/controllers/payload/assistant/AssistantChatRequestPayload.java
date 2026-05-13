package com.project.neurohelp.controllers.payload.assistant;

import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record AssistantChatRequestPayload(
		String sessionId,
		String userMessage,
		String locale,
		String country,
		String mode
) {
}

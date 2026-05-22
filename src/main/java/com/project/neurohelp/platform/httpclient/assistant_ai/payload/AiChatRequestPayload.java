package com.project.neurohelp.platform.httpclient.assistant_ai.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record AiChatRequestPayload(
		@JsonProperty("session_id") String sessionId,
		@JsonProperty("user_message") String userMessage,
		String locale,
		String country,
		String mode
) {
}

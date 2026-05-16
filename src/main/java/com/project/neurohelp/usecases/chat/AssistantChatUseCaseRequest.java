package com.project.neurohelp.usecases.chat;

import com.project.neurohelp.platform.usecase.UseCaseRequest;
import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record AssistantChatUseCaseRequest(
		String sessionId,
		String userMessage,
		String locale,
		String country,
		String mode
) implements UseCaseRequest {
}

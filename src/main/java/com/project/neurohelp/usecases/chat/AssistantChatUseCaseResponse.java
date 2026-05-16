package com.project.neurohelp.usecases.chat;

import com.project.neurohelp.platform.usecase.UseCaseResponse;
import io.soabase.recordbuilder.core.RecordBuilder;

import java.util.List;

@RecordBuilder
public record AssistantChatUseCaseResponse(
		String sessionId,
		String assistantMessage,
		String policyAction,
		List<String> riskFlags,
		List<Citation> citations
) implements UseCaseResponse {

	@RecordBuilder
	public record Citation(
			String sourceId,
			String title,
			String url,
			String chunkId
	) {
	}
}

package com.project.neurohelp.controllers.payload.assistant;

import io.soabase.recordbuilder.core.RecordBuilder;

import java.util.List;

@RecordBuilder
public record AssistantChatResponsePayload(
		String sessionId,
		String assistantMessage,
		String policyAction,
		List<String> riskFlags,
		List<CitationPayload> citations
) {

	@RecordBuilder
	public record CitationPayload(
			String sourceId,
			String title,
			String url,
			String chunkId
	) {
	}
}

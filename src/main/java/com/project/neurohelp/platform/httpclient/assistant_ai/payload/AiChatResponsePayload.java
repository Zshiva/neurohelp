package com.project.neurohelp.platform.httpclient.assistant_ai.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.soabase.recordbuilder.core.RecordBuilder;

import java.util.List;

@RecordBuilder
public record AiChatResponsePayload(
		@JsonProperty("assistant_message") String assistantMessage,
		@JsonProperty("policy_action") String policyAction,
		@JsonProperty("risk_flags") List<String> riskFlags,
		List<CitationPayload> citations
) {

	@RecordBuilder
	public record CitationPayload(
			@JsonProperty("source_id") String sourceId,
			String title,
			String url,
			@JsonProperty("chunk_id") String chunkId
	) {
	}
}

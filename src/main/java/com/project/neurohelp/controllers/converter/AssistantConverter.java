package com.project.neurohelp.controllers.converter;

import com.project.neurohelp.controllers.payload.assistant.AssistantChatRequestPayload;
import com.project.neurohelp.controllers.payload.assistant.AssistantChatResponsePayload;
import com.project.neurohelp.controllers.payload.assistant.AssistantChatResponsePayloadBuilder;
import com.project.neurohelp.controllers.payload.assistant.AssistantChatResponsePayloadCitationPayloadBuilder;
import com.project.neurohelp.usecases.chat.AssistantChatUseCaseRequest;
import com.project.neurohelp.usecases.chat.AssistantChatUseCaseRequestBuilder;
import com.project.neurohelp.usecases.chat.AssistantChatUseCaseResponse;

import java.util.List;

public class AssistantConverter {

	public static AssistantChatUseCaseRequest toRequest(AssistantChatRequestPayload payload) {
		return AssistantChatUseCaseRequestBuilder.builder()
				.sessionId(payload.sessionId())
				.userMessage(payload.userMessage())
				.locale(payload.locale())
				.country(payload.country())
				.mode(payload.mode())
				.build();
	}

	public static AssistantChatResponsePayload toPayload(AssistantChatUseCaseResponse response) {
		List<AssistantChatResponsePayload.CitationPayload> citations = response.citations() == null
				? List.of()
				: response.citations().stream().map(c -> AssistantChatResponsePayloadCitationPayloadBuilder.builder()
				.sourceId(c.sourceId())
				.title(c.title())
				.url(c.url())
				.chunkId(c.chunkId())
				.build()).toList();

		return AssistantChatResponsePayloadBuilder.builder()
				.sessionId(response.sessionId())
				.assistantMessage(response.assistantMessage())
				.policyAction(response.policyAction())
				.riskFlags(response.riskFlags() == null ? List.of() : response.riskFlags())
				.citations(citations)
				.build();
	}
}

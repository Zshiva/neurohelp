package com.project.neurohelp.platform.httpclient.assistant_ai;

import com.project.neurohelp.platform.exception.NeuroHelpErrorMessage;
import com.project.neurohelp.platform.exception.NeuroHelpException;
import com.project.neurohelp.platform.httpclient.assistant_ai.payload.AiChatRequestPayloadBuilder;
import com.project.neurohelp.platform.httpclient.assistant_ai.payload.AiChatResponsePayload;
import com.project.neurohelp.usecases.chat.AssistantChatUseCaseRequest;
import com.project.neurohelp.usecases.chat.AssistantChatUseCaseResponse;
import com.project.neurohelp.usecases.chat.AssistantChatUseCaseResponseCitationBuilder;
import com.project.neurohelp.usecases.chat.AssistantChatUseCaseResponseBuilder;
import com.project.neurohelp.usecases.chat.gateway.AiChatGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Component
@Slf4j
public class AiServiceClient implements AiChatGateway {

	private final RestClient restClient;

	public AiServiceClient(
			RestClient.Builder restClientBuilder,
			@Value("${neurohelp.ai-service.base-url:http://localhost:8000}") String baseUrl
	) {
		this.restClient = restClientBuilder.baseUrl(baseUrl).build();
	}

	@Override
	public AssistantChatUseCaseResponse chat(AssistantChatUseCaseRequest request) {
		try {
			log.debug("Calling AI service /v1/chat: sessionId={}", request.sessionId());
			var payload = AiChatRequestPayloadBuilder.builder()
					.sessionId(request.sessionId())
					.userMessage(request.userMessage())
					.locale(request.locale() == null || request.locale().isBlank() ? "en" : request.locale())
					.country(request.country() == null || request.country().isBlank() ? "NP" : request.country())
					.mode(request.mode() == null || request.mode().isBlank() ? "default" : request.mode())
					.build();

			AiChatResponsePayload response = restClient.post()
					.uri("/v1/chat")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.body(payload)
					.retrieve()
					.body(AiChatResponsePayload.class);

			if (response == null || response.assistantMessage() == null) {
				log.warn("AI service returned empty response: sessionId={}", request.sessionId());
				throw new NeuroHelpException(NeuroHelpErrorMessage.AI_SERVICE_BAD_RESPONSE);
			}

			List<AssistantChatUseCaseResponse.Citation> citations = response.citations() == null
					? List.of()
					: response.citations().stream().map(c -> AssistantChatUseCaseResponseCitationBuilder.builder()
					.sourceId(c.sourceId())
					.title(c.title())
					.url(c.url())
					.chunkId(c.chunkId())
					.build()).toList();

			return AssistantChatUseCaseResponseBuilder.builder()
					.sessionId(request.sessionId())
					.assistantMessage(response.assistantMessage())
					.policyAction(response.policyAction())
					.riskFlags(response.riskFlags() == null ? List.of() : response.riskFlags())
					.citations(citations)
					.build();
		} catch (RestClientException ex) {
			log.error("AI service call failed: sessionId={} error={}", request.sessionId(), ex.getMessage());
			throw new NeuroHelpException(NeuroHelpErrorMessage.AI_SERVICE_UNAVAILABLE);
		}
	}
}

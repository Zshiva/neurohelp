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
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.List;

@Component
@Slf4j
public class AiServiceClient implements AiChatGateway {

	private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(10);
	private static final Duration READ_TIMEOUT = Duration.ofSeconds(30);

	private final RestClient restClient;

	public AiServiceClient(
			RestClient.Builder restClientBuilder,
			@Value("${neurohelp.ai-service.base-url:http://localhost:8000}") String baseUrl
	) {
		HttpClient httpClient = HttpClient.newBuilder()
				.connectTimeout(CONNECT_TIMEOUT)
				.build();
		JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory(httpClient);
		factory.setReadTimeout(READ_TIMEOUT);

		this.restClient = restClientBuilder
				.baseUrl(baseUrl)
				.requestFactory(factory)
				.build();
	}

	@Override
	public AssistantChatUseCaseResponse chat(AssistantChatUseCaseRequest request) {
		try {
			log.debug("Calling AI service /v1/chat: sessionId={}", request.sessionId());

			// The request is already enriched with defaults from the use case,
			// so no need to apply defaults here. Just forward it as-is.
			var payload = AiChatRequestPayloadBuilder.builder()
					.sessionId(request.sessionId())
					.userMessage(request.userMessage())
					.locale(request.locale())  // Already enriched with default from use case
					.country(request.country())  // Already enriched with default from use case
					.mode(request.mode())  // Already enriched with default from use case
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
			log.error("AI service call failed: sessionId={}, error={}", request.sessionId(), ex.getMessage(), ex);
			throw new NeuroHelpException(NeuroHelpErrorMessage.AI_SERVICE_UNAVAILABLE);
		}
	}
}

package com.project.neurohelp.usecases.chat;

import com.project.neurohelp.platform.exception.NeuroHelpErrorMessage;
import com.project.neurohelp.platform.exception.NeuroHelpException;
import com.project.neurohelp.platform.usecase.UseCase;
import com.project.neurohelp.usecases.chat.gateway.AiChatGateway;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssistantChatUseCase implements UseCase<AssistantChatUseCaseRequest, AssistantChatUseCaseResponse> {

	private static final int MAX_USER_MESSAGE_LENGTH = 4000;
	private static final String DEFAULT_LOCALE = "en";
	private static final String DEFAULT_COUNTRY = "NP";
	private static final String DEFAULT_MODE = "default";

	private final AiChatGateway aiChatGateway;

	public AssistantChatUseCase(AiChatGateway aiChatGateway) {
		this.aiChatGateway = aiChatGateway;
	}

	@Override
	public Optional<AssistantChatUseCaseResponse> execute(AssistantChatUseCaseRequest request) {
		validate(request);
		AssistantChatUseCaseResponse response = aiChatGateway.chat(enrichRequest(request));
		if (response == null || response.assistantMessage() == null) {
			throw new NeuroHelpException(NeuroHelpErrorMessage.AI_SERVICE_BAD_RESPONSE);
		}
		return Optional.of(response);
	}

	/**
	 * Enriches the request with default values for optional fields.
	 * This ensures the gateway always receives a complete request.
	 */
	private AssistantChatUseCaseRequest enrichRequest(AssistantChatUseCaseRequest request) {
		return AssistantChatUseCaseRequestBuilder.builder()
				.sessionId(request.sessionId())
				.userMessage(request.userMessage())
				.locale(isBlank(request.locale()) ? DEFAULT_LOCALE : request.locale())
				.country(isBlank(request.country()) ? DEFAULT_COUNTRY : request.country())
				.mode(isBlank(request.mode()) ? DEFAULT_MODE : request.mode())
				.build();
	}

	private void validate(AssistantChatUseCaseRequest request) {
		if (request == null) {
			throw new NeuroHelpException(NeuroHelpErrorMessage.AI_SERVICE_BAD_RESPONSE);
		}
		if (request.sessionId() == null || request.sessionId().isBlank()) {
			throw new NeuroHelpException(NeuroHelpErrorMessage.CHAT_SESSION_ID_REQUIRED);
		}
		if (request.userMessage() == null || request.userMessage().isBlank()) {
			throw new NeuroHelpException(NeuroHelpErrorMessage.CHAT_USER_MESSAGE_REQUIRED);
		}
		if (request.userMessage().length() > MAX_USER_MESSAGE_LENGTH) {
			throw new NeuroHelpException(NeuroHelpErrorMessage.CHAT_USER_MESSAGE_TOO_LONG);
		}
	}

	private boolean isBlank(String value) {
		return value == null || value.isBlank();
	}
}

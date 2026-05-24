package com.project.neurohelp.controllers;

import com.project.neurohelp.controllers.converter.AssistantConverter;
import com.project.neurohelp.controllers.payload.assistant.AssistantChatRequestPayload;
import com.project.neurohelp.platform.exception.NeuroHelpErrorMessage;
import com.project.neurohelp.platform.exception.NeuroHelpException;
import com.project.neurohelp.platform.rest.RestResponse;
import com.project.neurohelp.usecases.chat.AssistantChatUseCase;
import com.project.neurohelp.usecases.chat.AssistantChatUseCaseRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AssistantController {

	private final AssistantChatUseCase assistantChatUseCase;

	public AssistantController(AssistantChatUseCase assistantChatUseCase) {
		this.assistantChatUseCase = assistantChatUseCase;
	}

	@PostMapping("/neurohelp/assistant/chat")
	public ResponseEntity<Object> chat(@RequestBody AssistantChatRequestPayload payload) {
		log.debug("Incoming assistant chat request: sessionId={}, locale={}, country={}, mode={}",
				payload.sessionId(), payload.locale(), payload.country(), payload.mode());
		AssistantChatUseCaseRequest request = AssistantConverter.toRequest(payload);
		var response = assistantChatUseCase.execute(request);
		if (response.isPresent()) {
			log.debug("Assistant chat response generated successfully: sessionId={}", request.sessionId());
			return ResponseEntity.ok(RestResponse.success(AssistantConverter.toPayload(response.get())));
		}
		log.warn("Assistant chat response missing/empty from AI service: sessionId={}", request.sessionId());
		throw new NeuroHelpException(NeuroHelpErrorMessage.AI_SERVICE_BAD_RESPONSE);
	}
}

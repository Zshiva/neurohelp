package com.project.neurohelp.controllers;

import com.project.neurohelp.controllers.converter.AssistantConverter;
import com.project.neurohelp.controllers.payload.assistant.AssistantChatRequestPayload;
import com.project.neurohelp.platform.rest.RestResponse;
import com.project.neurohelp.usecases.chat.AssistantChatUseCase;
import com.project.neurohelp.usecases.chat.AssistantChatUseCaseRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/neurohelp/api/v1/assistant")
@Slf4j
public class AssistantController {

	private final AssistantChatUseCase assistantChatUseCase;

	public AssistantController(AssistantChatUseCase assistantChatUseCase) {
		this.assistantChatUseCase = assistantChatUseCase;
	}

	/**
	 * Chat endpoint for mental health assistant.
	 * Accepts user message and contextual metadata (locale, country, mode).
	 *
	 * @param payload valid chat request containing sessionId, userMessage, and optional locale/country/mode
	 * @return REST response with assistant's message, citations, and safety flags
	 */
	@PostMapping("/chat")
	public ResponseEntity<RestResponse<?>> chat(@Valid @RequestBody AssistantChatRequestPayload payload) {
		log.debug("Incoming assistant chat request: sessionId={}, locale={}, country={}, mode={}",
				payload.sessionId(), payload.locale(), payload.country(), payload.mode());

		AssistantChatUseCaseRequest request = AssistantConverter.toRequest(payload);
		var response = assistantChatUseCase.execute(request);

		log.debug("Assistant chat response generated successfully: sessionId={}", request.sessionId());
		return ResponseEntity.ok(RestResponse.success(AssistantConverter.toPayload(response.get())));
	}
}

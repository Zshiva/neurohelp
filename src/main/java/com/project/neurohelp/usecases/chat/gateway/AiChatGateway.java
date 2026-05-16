package com.project.neurohelp.usecases.chat.gateway;

import com.project.neurohelp.usecases.chat.AssistantChatUseCaseRequest;
import com.project.neurohelp.usecases.chat.AssistantChatUseCaseResponse;

public interface AiChatGateway {
    AssistantChatUseCaseResponse chat(AssistantChatUseCaseRequest request);
}


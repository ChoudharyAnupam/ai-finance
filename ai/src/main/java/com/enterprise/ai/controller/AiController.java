package com.enterprise.ai.controller;

import com.enterprise.ai.service.AiOrchestratorService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiOrchestratorService aiOrchestratorService;

    private final ChatClient chatClient;

    public AiController(AiOrchestratorService aiOrchestratorService, ChatClient.Builder chatClientBuilder) {
        this.aiOrchestratorService = aiOrchestratorService;
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/audit/{id}")
    public Mono<String> auditTransaction(@PathVariable String id){
        return aiOrchestratorService.processAndAuditTransaction(id);
    }
}


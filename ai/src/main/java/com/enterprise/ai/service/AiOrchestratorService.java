package com.enterprise.ai.service;

import com.enterprise.ai.dto.TransactionDto;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.ai.vectorstore.VectorStore;

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AiOrchestratorService {

    private final ChatClient chatClient;
    private final WebClient webClient;
    private final VectorStore vectorStore;

    public AiOrchestratorService(ChatClient.Builder chatClient, WebClient webClient, VectorStore vectorStore) {
        this.chatClient = chatClient.build();
        this.webClient = webClient;
        this.vectorStore = vectorStore;
    }

    public Mono<String> processAndAuditTransaction(String transactionId) {

        return this.webClient.get()
                .uri("/api/transactions/{id}", transactionId)
                .retrieve()
                .bodyToMono(TransactionDto.class)
                .flatMap(transaction -> {
                    String searchQuery = String.format(
                            "Transaction of AED %.2f categorized as %s with status $s",
                            transaction.amount(), transaction.category(), transaction.status()
                    );


                    List<Document> matchedRules = vectorStore.similaritySearch(
                            SearchRequest.builder()
                                    .query(searchQuery)
                                    .topK(1)
                                    .similarityThreshold(0.6)
                                    .build()
                    );

                    String legalRulesText = matchedRules.stream()
                            .map(Document::getText)
                            .collect(Collectors.joining("\n"));

                    String systemPrompt = """
                        You are an expert enterprise compliance system working inside a Tier-1 bank in the UAE.
                        You must audit the provided raw transactional data strictly against UAE Central Bank, DFSA, and FSRA regulations.
                        
                        You are required to output your final evaluation using the following exact enterprise schema:
                        
                        [UAE REGULATORY AUDIT REPORT]
                        - TRANSACTION ID: <id>
                        - RISK RATING: [LOW / MEDIUM / HIGH / CRITICAL]
                        - UAE CENTRAL BANK VIOLATION: [NONE or specify exact regulatory clause broken]
                        - FIU STR FILING MANDATORY: [YES / NO]
                        - REGIONAL RISK ANALYSIS: <Detailed financial analysis highlighting AED thresholds and GCC implications>
                        - REQUIRED COMPLIANCE ACTION: <Step-by-step resolution for bank operational teams>
                        
                        Reference Context Rules:
                        """ + legalRulesText;
                    String userPrompt = String.format("Analyze Transaction IS: %s, Amount: AED %.2f", transaction.id(), transaction.amount());

                    String finalAnalysis = chatClient.prompt()
                            .system(systemPrompt)
                            .user(userPrompt)
                            .call()
                            .content();

//                    SearchRequest searchRequest = SearchRequest.builder()
//                            .query(semanticQuery)
//                            .topK(2)
//                            .similarityThreshold(0.7)
//                            .build();
//
//                    List<Document> matchedRules = vectorStore.similaritySearch(searchRequest);

//                    String legalContext = matchedRules.stream()
//                            .map(Document::getText)
//                            .collect(Collectors.joining("\n-"));
//
//                    String systemInstructions = STR."""
//You are a Senior FinTech Compliance Officer in Dubai. Audit the transaction strictly against these corporate compliance rules:
//\{legalContext}""";
//
//                    String userQuery = String.format(
//                            "Review transaction: ID=%s, RickAmount=AED %.2f, Category=%s. Identify any direct violations.",
//                            transaction.id(), transaction.amount(), transaction.category()
//                    );
//
//                    String aiResponse = chatClient.prompt()
//                            .system(systemInstructions)
//                            .user(userQuery)
//                            .call()
//                            .content();

                    return Mono.just(finalAnalysis);
                });
    }
}

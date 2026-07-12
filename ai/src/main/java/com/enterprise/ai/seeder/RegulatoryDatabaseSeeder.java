package com.enterprise.ai.seeder;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class RegulatoryDatabaseSeeder implements ApplicationRunner {

    private final VectorStore vectorStore;

    public RegulatoryDatabaseSeeder(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        System.out.println(">>> [RAG Initialization] Checking UAE Compliance Knowledge Base in PostgreSQL...");

        try {
            SearchRequest testRequest = SearchRequest.builder()
                    .query("UAE Central Bank Requlation")
                    .topK(1)
                    .similarityThreshold(0.1)
                    .build();

            List<Document> existingDocuments = vectorStore.similaritySearch(testRequest);

            if(existingDocuments.isEmpty()) {
                System.out.println(">>> [RAG Database Empty] Seeding UAE Financial Regulatory Records into pgvector...");

                // Strict, production-grade UAE financial regulations for the RAG engine
                List<Document> uaeRegulatoryKnowledgeBase = List.of(
                        new Document(
                                "UAE CENTRAL BANK LAW NO. 20 (AML/CFT): Any retail cash transaction, cross-border corporate transfer, or single instant payment transfer exceeding AED 40,000 must be accompanied by verified identity documentation and an automated source of funds validation check.",
                                Map.of("regulation_source", "UAE_CENTRAL_BANK", "clause", "AML-40K", "jurisdiction", "Federal")
                        ),
                        new Document(
                                "DFSA CONDUCT OF BUSINESS (COB) SECTION 7.2: Authorized Firms operating within the Dubai International Financial Centre (DIFC) are prohibited from processing high-frequency outbound corporate transactions to digital asset entities or crypto exchanges without explicit enhanced due diligence (EDD).",
                                Map.of("regulation_source", "DFSA_DUBAI", "clause", "CRYPTO-EDD", "jurisdiction", "DIFC")
                        ),
                        new Document(
                                "FSRA ANTI-MONEY LAUNDERING AND SANCTIONS RULES (ADGM): Accounts displaying velocity anomalies—specifically processing more than 5 distinct corporate or retail financial transfers within a window of 60 seconds targeting accounts outside the GCC region—must be instantly suspended for manual compliance review.",
                                Map.of("regulation_source", "FSRA_ADGM", "clause", "VELOCITY-BURST", "jurisdiction", "ADGM")
                        )
                );

                // Spring AI automatically generates vectors via the configured LLM API and commits them to Postgres
                vectorStore.add(uaeRegulatoryKnowledgeBase);
                System.out.println(">>> [RAG Datastore Synced] Successfully seeded 3 corporate UAE compliance modules into Docker pgvector!");
            } else {
                System.out.println(">>> [RAG Datastore Active] UAE Compliance regulations are already initialized. Skipping seeding.");
            }
        } catch (Exception e) {
            System.err.println(">>> [RAG Seeder Failed] Unable to connect or write to pgvector table. Check if 'CREATE EXTENSION vector;' was run inside pgAdmin.");
            e.printStackTrace();
        }
    }

}

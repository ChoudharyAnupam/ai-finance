package com.enterprise.ai;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelRequest;

@SpringBootApplication
public class AiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiApplication.class, args);
	}


    /*@Bean
    CommandLineRunner embeddingTest(EmbeddingModel embeddingModel) {
        return args -> {
            System.out.println("EmbeddingModel implementation: "
                    + embeddingModel.getClass().getName());

            try {
                var response = embeddingModel.embedForResponse(
                        java.util.List.of("Hello World"));

                System.out.println("Embedding dimensions: "
                        + response.getResults().getFirst().getOutput().length);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };*/
//    }

}

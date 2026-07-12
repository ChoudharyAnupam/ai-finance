package com.enterprise.ai.config;

//import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class WebClientConfig {

    @Value("${core.service.url}")
    private String coreServiceUrl;

    @Value("${spring.ai.aws.credentials.access-key}")
    private String accessKey;

    @Value("${spring.ai.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${spring.ai.aws.region}")
    private String region;

    @Bean
    public WebClient coreServiceClient() {
        return WebClient.builder()
                .baseUrl(coreServiceUrl)
                .build();
    }

//    @Bean
//    public ObjectMapper objectMapper() {
//        return new ObjectMapper();
//    }

//    @Bean
//    public EmbeddingModel embeddingModel(JsonMapper jsonMapper) {
//        // FIXED: Added java.time.Duration and swapped ObjectMapper for JsonMapper
//        TitanEmbeddingBedrockApi titanEmbeddingBedrockApi = new TitanEmbeddingBedrockApi(
//                "amazon.titan-embed-text-v2:0",
//                StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)),
//                Region.of(region).id(),
//                jsonMapper,
//                Duration.ofSeconds(60) // Crucial final missing parameter
//        );
//
//        // 2. Build the missing Connection Details argument required by the constructor
//        BedrockEmbeddingConnectionDetails connectionDetails = BedrockEmbeddingConnectionDetails.builder()
//                .withRegion(region)
//                .build();
//
//        // FIXED: Both arguments are now successfully provided to the constructor
//        return new BedrockTitanEmbeddingModel(titanEmbeddingBedrockApi, connectionDetails);
//    }
}

package com.enterprise.ai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelRequest;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelResponse;

@RestController
@RequestMapping("/sdk")
public class SdkController {

    private final BedrockRuntimeClient client;

    public SdkController() {
        this.client = BedrockRuntimeClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    @GetMapping
    public String test() {

        String body = """
        {
          "inputText":"Hello World"
        }
        """;

        InvokeModelResponse response = client.invokeModel(
                InvokeModelRequest.builder()
                        .modelId("amazon.titan-embed-text-v2:0")
                        .contentType("application/json")
                        .accept("application/json")
                        .body(SdkBytes.fromUtf8String(body))
                        .build());

        return response.body().asUtf8String();
    }
}

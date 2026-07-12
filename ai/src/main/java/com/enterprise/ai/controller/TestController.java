package com.enterprise.ai.controller;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private final EmbeddingModel embeddingModel;

    public TestController(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @GetMapping
    public String test() {

        float[] embedding = embeddingModel.embed("Hello World");

        return "Embedding size = " + embedding.length;
    }

    @GetMapping("/class")
    public String clazz() {
        return embeddingModel.getClass().getName();
    }
}
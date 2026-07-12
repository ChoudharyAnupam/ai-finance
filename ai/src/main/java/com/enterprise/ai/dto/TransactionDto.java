package com.enterprise.ai.dto;

public record TransactionDto(
        Integer id,
        Double amount,
        String category,
        String status
) {
}

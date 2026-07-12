package com.enterprise.core.controller;

import com.enterprise.core.entity.Transaction;
import com.enterprise.core.repository.TransactionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class CoreTransactionController {

    private final TransactionRepository transactionRepository;

    public CoreTransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getRawTransactionData(@PathVariable Long id) {
        return transactionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Transaction> createMockTransaction(@RequestBody Transaction transaction) {
        Transaction savedTx = transactionRepository.save(transaction);
        return ResponseEntity.ok(savedTx);
    }
}

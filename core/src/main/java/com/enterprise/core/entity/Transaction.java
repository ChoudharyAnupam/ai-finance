package com.enterprise.core.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.AnyDiscriminatorImplicitValues;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tx_seq")
    @SequenceGenerator(name = "tx_seq", sequenceName = "transaction_sequence", allocationSize = 1)
    private Long id;
    private String accountId;
    private double amount;
    private String category;
    private String status;
}


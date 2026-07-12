package com.enterprise.ai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "compliance_rules")
@Getter
@Setter
public class ComplianceRule {

    @Id
    @Column(columnDefinition = "uuid") // Forces PostgreSQL to use native UUID type
    private UUID id;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "metadata")
    @JdbcTypeCode(SqlTypes.JSON)
    private String metadata;

    @Column(columnDefinition = "vector(1536)")
    private double[] embedding;

}

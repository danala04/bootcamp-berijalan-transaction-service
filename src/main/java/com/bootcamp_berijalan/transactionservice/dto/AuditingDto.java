package com.bootcamp_berijalan.transactionservice.dto;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.Instant;

@Data
@MappedSuperclass
public class AuditingDto {
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();
    private Instant deletedAt;
}

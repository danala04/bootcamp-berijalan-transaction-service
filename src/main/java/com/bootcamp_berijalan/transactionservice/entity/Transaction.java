package com.bootcamp_berijalan.transactionservice.entity;

import com.bootcamp_berijalan.transactionservice.dto.AuditingDto;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Table
@Data
public class Transaction extends AuditingDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "type", referencedColumnName = "id")
    private TransactionType type;

    private double amount;

    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "id")
    private Category category;

    private Instant date;

    @ManyToOne
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    private Wallet wallet;
}

package com.bootcamp_berijalan.transactionservice.entity;

import com.bootcamp_berijalan.transactionservice.dto.AuditingDto;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Table
@Data
public class Transfer extends AuditingDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "source_wallet_id", referencedColumnName = "id")
    private Wallet sourceWallet;

    @ManyToOne
    @JoinColumn(name = "destination_wallet_id", referencedColumnName = "id")
    private Wallet destinationWallet;

    private double amount;

    private Instant transferDate = Instant.now();
}

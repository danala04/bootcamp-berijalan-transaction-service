package com.bootcamp_berijalan.transactionservice.entity;

import com.bootcamp_berijalan.transactionservice.dto.AuditingDto;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Wallet extends AuditingDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "type", referencedColumnName = "id")
    private WalletType walletType;

    private Long userId;
}

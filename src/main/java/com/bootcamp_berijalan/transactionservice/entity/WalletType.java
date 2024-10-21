package com.bootcamp_berijalan.transactionservice.entity;

import com.bootcamp_berijalan.transactionservice.dto.AuditingDto;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class WalletType extends AuditingDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
}

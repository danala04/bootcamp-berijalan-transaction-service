package com.bootcamp_berijalan.transactionservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class ResTransactionDto {
    private Integer id;
    private String date;
    private String name;
    private ResTransactionTypeDto type;
    private Double amount;
    private ResCategoryDto category;
}

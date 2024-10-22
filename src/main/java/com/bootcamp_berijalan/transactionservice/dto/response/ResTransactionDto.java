package com.bootcamp_berijalan.transactionservice.dto.response;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class ResTransactionDto {
    @Nullable
    private Integer id;
    private String date;
    private String name;
    private ResTransactionTypeDto type;
    private Double amount;
    private ResCategoryDto category;
}

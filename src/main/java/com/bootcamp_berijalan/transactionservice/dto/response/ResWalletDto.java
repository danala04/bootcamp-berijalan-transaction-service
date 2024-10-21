package com.bootcamp_berijalan.transactionservice.dto.response;

import com.bootcamp_berijalan.transactionservice.entity.WalletType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResWalletDto {
    private Integer id;
    private String name;
    private ResWalletTypeDto type;
    private Double amount;
    @JsonProperty("total_income")
    private Double totalIncome;
    @JsonProperty("total_expense")
    private Double totalExpense;
}

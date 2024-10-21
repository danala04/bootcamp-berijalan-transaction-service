package com.bootcamp_berijalan.transactionservice.dto.response;

import com.bootcamp_berijalan.transactionservice.entity.WalletType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResWalletDto {
    private Integer id;
    private String name;
    private ResWalletTypeDto walletType;
    private Double amount;
    private Double totalIncome;
    private Double totalExpense;
}

package com.bootcamp_berijalan.transactionservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResTransferDto {
    private Integer id;
    private ResWalletDto sourceWallet;
    private ResWalletDto targetWallet;
    private Double amount;
    private String date;
}

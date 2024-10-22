package com.bootcamp_berijalan.transactionservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReqSaveTransferDto {
    @JsonProperty("source_wallet_id")
    @NotNull(message = "Source wallet ID cannot be null")
    private Integer sourceWalletId;

    @JsonProperty("destination_wallet_id")
    @NotNull(message = "Destination wallet ID cannot be null")
    private Integer destinationWalletId;

    @NotNull(message = "Amount cannot be null")
    private Double amount;
}

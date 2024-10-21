package com.bootcamp_berijalan.transactionservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReqSaveTransactionDto {
    @NotBlank(message = "Date must not be blank")
    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{2}$", message = "Date must be in the format dd/MM/yy")
    private String date;

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotNull(message = "Type ID must not be null")
    @JsonProperty("type_id")
    private Integer typeId;

    @NotNull(message = "Category ID must not be null")
    @JsonProperty("category_id")
    private Integer categoryId;

    @NotNull(message = "Amount must not be null")
    private Double amount;

    @NotNull(message = "Wallet ID must not be null")
    @JsonProperty("wallet_id")
    private Integer walletId;
}

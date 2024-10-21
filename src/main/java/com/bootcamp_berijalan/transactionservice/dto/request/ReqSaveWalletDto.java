package com.bootcamp_berijalan.transactionservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReqSaveWalletDto {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Type ID cannot be null")
    @JsonProperty("type_id")
    private Integer typeId;
}

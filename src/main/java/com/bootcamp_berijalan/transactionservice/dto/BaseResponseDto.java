package com.bootcamp_berijalan.transactionservice.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@Builder
public class BaseResponseDto {
    private HttpStatus status;
    private String description;
    private Map<String, Object> data;
}
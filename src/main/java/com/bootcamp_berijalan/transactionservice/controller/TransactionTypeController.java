package com.bootcamp_berijalan.transactionservice.controller;

import com.bootcamp_berijalan.transactionservice.dto.BaseResponseDto;
import com.bootcamp_berijalan.transactionservice.service.TransactionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/transaction_types")
public class TransactionTypeController {
    @Autowired
    TransactionTypeService transactionTypeService;

    @GetMapping
    public ResponseEntity<BaseResponseDto> getTransactionTypes() {
        BaseResponseDto response = transactionTypeService.getAll();
        return new ResponseEntity<>(response, response.getStatus());
    }
}

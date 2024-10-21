package com.bootcamp_berijalan.transactionservice.controller;

import com.bootcamp_berijalan.transactionservice.dto.BaseResponseDto;
import com.bootcamp_berijalan.transactionservice.dto.request.ReqSaveTransactionDto;
import com.bootcamp_berijalan.transactionservice.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<BaseResponseDto> save(@Valid @RequestBody ReqSaveTransactionDto request){
        BaseResponseDto response = transactionService.save(request);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<BaseResponseDto> getTransactionsByWalletId(@RequestParam(value = "walletId", required = true) Long walletId) {
        BaseResponseDto response = transactionService.getTransactionsByWalletId(walletId);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<BaseResponseDto> update(@PathVariable Long id, @Valid @RequestBody ReqSaveTransactionDto request) {
        BaseResponseDto response = transactionService.update(id, request);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('user')")
    public ResponseEntity<BaseResponseDto> delete(@PathVariable Long id) {
        BaseResponseDto response = transactionService.delete(id);
        return new ResponseEntity<>(response, response.getStatus());
    }
}

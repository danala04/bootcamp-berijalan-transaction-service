package com.bootcamp_berijalan.transactionservice.service;

import com.bootcamp_berijalan.transactionservice.dto.BaseResponseDto;
import com.bootcamp_berijalan.transactionservice.dto.request.ReqSaveTransactionDto;

public interface TransactionService {
    BaseResponseDto save(ReqSaveTransactionDto request);
    BaseResponseDto getTransactionsByWalletId(Long walletId);
    BaseResponseDto update(Long id, ReqSaveTransactionDto request);
    BaseResponseDto delete(Long id);
}

package com.bootcamp_berijalan.transactionservice.service;

import com.bootcamp_berijalan.transactionservice.dto.BaseResponseDto;
import com.bootcamp_berijalan.transactionservice.dto.request.ReqSaveTransactionDto;

public interface TransactionService {
    BaseResponseDto save(ReqSaveTransactionDto request);
}

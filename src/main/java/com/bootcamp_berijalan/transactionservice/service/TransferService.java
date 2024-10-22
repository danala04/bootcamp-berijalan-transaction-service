package com.bootcamp_berijalan.transactionservice.service;

import com.bootcamp_berijalan.transactionservice.dto.BaseResponseDto;
import com.bootcamp_berijalan.transactionservice.dto.request.ReqSaveTransferDto;

public interface TransferService {
    BaseResponseDto save(ReqSaveTransferDto request);
}

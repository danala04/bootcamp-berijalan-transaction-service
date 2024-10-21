package com.bootcamp_berijalan.transactionservice.service;

import com.bootcamp_berijalan.transactionservice.dto.BaseResponseDto;
import com.bootcamp_berijalan.transactionservice.dto.request.ReqSaveWalletDto;
import com.bootcamp_berijalan.transactionservice.util.CustomUserDetails;

public interface WalletService {
    BaseResponseDto save(CustomUserDetails customUserDetails, ReqSaveWalletDto requests);
}

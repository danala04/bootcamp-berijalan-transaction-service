package com.bootcamp_berijalan.transactionservice.service.impl;

import com.bootcamp_berijalan.transactionservice.dto.BaseResponseDto;
import com.bootcamp_berijalan.transactionservice.dto.request.ReqSaveWalletDto;
import com.bootcamp_berijalan.transactionservice.dto.response.ResWalletDto;
import com.bootcamp_berijalan.transactionservice.entity.Wallet;
import com.bootcamp_berijalan.transactionservice.entity.WalletType;
import com.bootcamp_berijalan.transactionservice.exception.WalletTypeNotFoundException;
import com.bootcamp_berijalan.transactionservice.repository.WalletRepository;
import com.bootcamp_berijalan.transactionservice.repository.WalletTypeRepository;
import com.bootcamp_berijalan.transactionservice.service.WalletService;
import com.bootcamp_berijalan.transactionservice.util.Constant;
import com.bootcamp_berijalan.transactionservice.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    WalletRepository walletRepository;

    @Autowired
    WalletTypeRepository walletTypeRepository;

    @Override
    public BaseResponseDto save(CustomUserDetails customUserDetails, ReqSaveWalletDto requests) {
        Optional<WalletType> walletType = walletTypeRepository.findById(requests.getTypeId().longValue());

        if(walletType.isEmpty()){
            throw new WalletTypeNotFoundException("Wallet type with id " + requests.getTypeId() + " not found");
        }

        Wallet wallet = new Wallet();
        wallet.setName(requests.getName());
        wallet.setWalletType(walletType.get());
        wallet.setUserId(customUserDetails.getId());

        walletRepository.save(wallet);

        ResWalletDto response = new ResWalletDto(
                wallet.getId(),
                wallet.getName(),
                wallet.getWalletType(),
                0.0,
                0.0,
                0.0
        );

        Map<String, Object> data = new HashMap<>();
        data.put(Constant.WALLET, response);

        return BaseResponseDto.builder()
                .status(HttpStatus.OK)
                .description(Constant.SUCCESS)
                .data(data)
                .build();
    }
}

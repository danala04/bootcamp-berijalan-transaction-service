package com.bootcamp_berijalan.transactionservice.service.impl;

import com.bootcamp_berijalan.transactionservice.dto.BaseResponseDto;
import com.bootcamp_berijalan.transactionservice.dto.request.ReqSaveWalletDto;
import com.bootcamp_berijalan.transactionservice.dto.response.ResTotalIncomeExpenseDto;
import com.bootcamp_berijalan.transactionservice.dto.response.ResWalletDto;
import com.bootcamp_berijalan.transactionservice.dto.response.ResWalletTypeDto;
import com.bootcamp_berijalan.transactionservice.entity.Wallet;
import com.bootcamp_berijalan.transactionservice.entity.WalletType;
import com.bootcamp_berijalan.transactionservice.exception.WalletNotFoundException;
import com.bootcamp_berijalan.transactionservice.exception.WalletTypeNotFoundException;
import com.bootcamp_berijalan.transactionservice.repository.WalletRepository;
import com.bootcamp_berijalan.transactionservice.repository.WalletTypeRepository;
import com.bootcamp_berijalan.transactionservice.service.TransactionService;
import com.bootcamp_berijalan.transactionservice.service.WalletService;
import com.bootcamp_berijalan.transactionservice.service.factory.WalletFactoryService;
import com.bootcamp_berijalan.transactionservice.util.Constant;
import com.bootcamp_berijalan.transactionservice.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    WalletRepository walletRepository;

    @Autowired
    WalletTypeRepository walletTypeRepository;

    @Autowired
    WalletFactoryService walletFactoryService;

    @Override
    public BaseResponseDto save(CustomUserDetails customUserDetails, ReqSaveWalletDto requests) {
        Optional<WalletType> walletType = walletTypeRepository.findActiveById(requests.getTypeId().longValue());

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
                new ResWalletTypeDto(
                        walletType.get().getId(),
                        walletType.get().getName()
                ),
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

    @Override
    public BaseResponseDto getAllWalletsByUserId(CustomUserDetails customUserDetails) {
        List<Wallet> wallets = walletRepository.findActiveByUserId(customUserDetails.getId());

        if (wallets.isEmpty()) {
            throw new WalletNotFoundException("Wallets with userId " + customUserDetails.getId() + " not found!");
        }

        List<ResWalletDto> walletResponses = wallets.stream()
                .map(wallet -> {
                    ResTotalIncomeExpenseDto amount = walletFactoryService.setTotalIncomeExpense(wallet.getId());

                    return new ResWalletDto(
                            wallet.getId(),
                            wallet.getName(),
                            new ResWalletTypeDto(
                                    wallet.getWalletType().getId(),
                                    wallet.getWalletType().getName()
                            ),
                            amount.getTotalAmount(),
                            amount.getTotalIncome(),
                            amount.getTotalExpense()
                    );
                })
                .toList();


        Map<String, Object> data = new HashMap<>();
        data.put(Constant.WALLETS, walletResponses);

        return BaseResponseDto.builder()
                .status(HttpStatus.OK)
                .description(Constant.SUCCESS)
                .data(data)
                .build();
    }

    @Override
    public BaseResponseDto getWalletById(Long id) {
        Optional<Wallet> wallet = walletRepository.findActiveById(id);

        if(wallet.isEmpty()){
            throw new WalletNotFoundException("Wallet with id " + id + " not found!");
        }

        ResTotalIncomeExpenseDto amount = walletFactoryService.setTotalIncomeExpense(id);

        ResWalletDto response = new ResWalletDto(
                wallet.get().getId(),
                wallet.get().getName(),
                new ResWalletTypeDto(
                        wallet.get().getWalletType().getId(),
                        wallet.get().getWalletType().getName()
                ),
                amount.getTotalAmount(),
                amount.getTotalIncome(),
                amount.getTotalExpense()
        );

        Map<String, Object> data = new HashMap<>();
        data.put(Constant.WALLET, response);

        return BaseResponseDto.builder()
                .status(HttpStatus.OK)
                .description(Constant.SUCCESS)
                .data(data)
                .build();
    }

    @Override
    public BaseResponseDto update(Long id, ReqSaveWalletDto requests) {
        Optional<Wallet> wallet = walletRepository.findActiveById(id);

        if(wallet.isEmpty()){
            throw new WalletNotFoundException("Wallet with id " + id + " not found!");
        }

        Optional<WalletType> walletType = walletTypeRepository.findActiveById(requests.getTypeId().longValue());

        if(walletType.isEmpty()){
            throw new WalletTypeNotFoundException("Wallet type with id " + requests.getTypeId() + " not found");
        }

        Wallet updatedWallet = wallet.get();

        updatedWallet.setId(wallet.get().getId());
        updatedWallet.setName(requests.getName());
        updatedWallet.setWalletType(walletType.get());
        updatedWallet.setUpdatedAt(Instant.now());

        walletRepository.save(updatedWallet);

        ResTotalIncomeExpenseDto amount = walletFactoryService.setTotalIncomeExpense(id);

        ResWalletDto response = new ResWalletDto(
                updatedWallet.getId(),
                updatedWallet.getName(),
                new ResWalletTypeDto(
                        walletType.get().getId(),
                        walletType.get().getName()
                ),
                amount.getTotalAmount(),
                amount.getTotalIncome(),
                amount.getTotalExpense()
        );

        Map<String, Object> data = new HashMap<>();
        data.put(Constant.WALLET, response);

        return BaseResponseDto.builder()
                .status(HttpStatus.OK)
                .description(Constant.SUCCESS)
                .data(data)
                .build();
    }

    @Override
    public BaseResponseDto delete(Long id) {
        Optional<Wallet> wallet = walletRepository.findActiveById(id);

        if (wallet.isEmpty()) {
            throw new WalletNotFoundException("Wallet with id " + id + " not found!");
        }

        walletRepository.softDelete(id);

        Map<String, Object> data = new HashMap<>();

        return BaseResponseDto.builder()
                .status(HttpStatus.OK)
                .description(Constant.SUCCESS)
                .data(data)
                .build();
    }
}

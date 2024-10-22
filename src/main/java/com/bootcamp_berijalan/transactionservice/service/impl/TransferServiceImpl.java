package com.bootcamp_berijalan.transactionservice.service.impl;

import com.bootcamp_berijalan.transactionservice.dto.BaseResponseDto;
import com.bootcamp_berijalan.transactionservice.dto.request.ReqSaveTransferDto;
import com.bootcamp_berijalan.transactionservice.dto.response.ResTotalIncomeExpenseDto;
import com.bootcamp_berijalan.transactionservice.dto.response.ResTransferDto;
import com.bootcamp_berijalan.transactionservice.dto.response.ResWalletDto;
import com.bootcamp_berijalan.transactionservice.dto.response.ResWalletTypeDto;
import com.bootcamp_berijalan.transactionservice.entity.Transfer;
import com.bootcamp_berijalan.transactionservice.entity.Wallet;
import com.bootcamp_berijalan.transactionservice.exception.WalletNotFoundException;
import com.bootcamp_berijalan.transactionservice.repository.TransferRepository;
import com.bootcamp_berijalan.transactionservice.repository.WalletRepository;
import com.bootcamp_berijalan.transactionservice.service.TransferService;
import com.bootcamp_berijalan.transactionservice.service.factory.WalletFactoryService;
import com.bootcamp_berijalan.transactionservice.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Service
public class TransferServiceImpl implements TransferService {
    @Autowired
    WalletRepository walletRepository;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private WalletFactoryService walletFactoryService;

    @Override
    public BaseResponseDto save(ReqSaveTransferDto request) {
        Optional<Wallet> walletSource = walletRepository.findActiveById(request.getSourceWalletId().longValue());

        if(walletSource.isEmpty()) {
            throw new WalletNotFoundException("Wallet with id " + request.getSourceWalletId() + " not found");
        }

        Optional<Wallet> walletDestination = walletRepository.findActiveById(request.getDestinationWalletId().longValue());

        if(walletDestination.isEmpty()) {
            throw new WalletNotFoundException("Wallet with id " + request.getDestinationWalletId() + " not found");
        }

        Transfer transfer = new Transfer();
        transfer.setSourceWallet(walletSource.get());
        transfer.setDestinationWallet(walletDestination.get());
        transfer.setAmount(request.getAmount());

        transferRepository.save(transfer);

        ResTotalIncomeExpenseDto amountSource = walletFactoryService.setTotalIncomeExpense(walletSource.get().getId());
        ResTotalIncomeExpenseDto amountDestination = walletFactoryService.setTotalIncomeExpense(walletDestination.get().getId());

        ResTransferDto response = new ResTransferDto(
                transfer.getId(),
                new ResWalletDto(
                        walletSource.get().getId(),
                        walletSource.get().getName(),
                        new ResWalletTypeDto(
                                walletSource.get().getWalletType().getId(),
                                walletSource.get().getWalletType().getName()
                        ),
                        amountSource.getTotalAmount(),
                        amountSource.getTotalIncome(),
                        amountSource.getTotalExpense()
                ),
                new ResWalletDto(
                        walletDestination.get().getId(),
                        walletDestination.get().getName(),
                        new ResWalletTypeDto(
                                walletDestination.get().getWalletType().getId(),
                                walletDestination.get().getWalletType().getName()
                        ),
                        amountDestination.getTotalAmount(),
                        amountDestination.getTotalIncome(),
                        amountDestination.getTotalExpense()
                ),
                transfer.getAmount(),
                convertToDateString(transfer.getTransferDate())
        );

        Map<String, Object> data = new HashMap<>();
        data.put(Constant.TRANSFER, response);

        return BaseResponseDto.builder()
                .status(HttpStatus.OK)
                .description(Constant.SUCCESS)
                .data(data)
                .build();
    }

    protected static String convertToDateString(Instant instant) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy", Locale.ENGLISH);

        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        return localDateTime.format(formatter);
    }
}

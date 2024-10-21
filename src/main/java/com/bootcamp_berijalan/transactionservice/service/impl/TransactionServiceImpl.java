package com.bootcamp_berijalan.transactionservice.service.impl;

import com.bootcamp_berijalan.transactionservice.dto.BaseResponseDto;
import com.bootcamp_berijalan.transactionservice.dto.request.ReqSaveTransactionDto;
import com.bootcamp_berijalan.transactionservice.dto.response.ResCategoryDto;
import com.bootcamp_berijalan.transactionservice.dto.response.ResTransactionDto;
import com.bootcamp_berijalan.transactionservice.dto.response.ResTransactionTypeDto;
import com.bootcamp_berijalan.transactionservice.entity.Category;
import com.bootcamp_berijalan.transactionservice.entity.Transaction;
import com.bootcamp_berijalan.transactionservice.entity.TransactionType;
import com.bootcamp_berijalan.transactionservice.entity.Wallet;
import com.bootcamp_berijalan.transactionservice.exception.CategoryNotFoundException;
import com.bootcamp_berijalan.transactionservice.exception.TransactionNotFoundException;
import com.bootcamp_berijalan.transactionservice.exception.TransactionTypeNotFoundException;
import com.bootcamp_berijalan.transactionservice.exception.WalletNotFoundException;
import com.bootcamp_berijalan.transactionservice.repository.CategoryRepository;
import com.bootcamp_berijalan.transactionservice.repository.TransactionRepository;
import com.bootcamp_berijalan.transactionservice.repository.TransactionTypeRepository;
import com.bootcamp_berijalan.transactionservice.repository.WalletRepository;
import com.bootcamp_berijalan.transactionservice.service.TransactionService;
import com.bootcamp_berijalan.transactionservice.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TransactionTypeRepository transactionTypeRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    WalletRepository walletRepository;

    @Override
    public BaseResponseDto save(ReqSaveTransactionDto request) {
        Optional<TransactionType> transactionType = transactionTypeRepository.findActiveById(request.getTypeId().longValue());
        if(transactionType.isEmpty()) {
            throw new TransactionTypeNotFoundException("Transaction type with id " + request.getTypeId() + " not found");
        }

        Optional<Category> category = categoryRepository.findActiveById(request.getCategoryId().longValue());
        if(category.isEmpty()) {
            throw new CategoryNotFoundException("Category with id " + request.getCategoryId() + " not found");
        }

        Optional<Wallet> wallet = walletRepository.findActiveById(request.getWalletId().longValue());
        if(wallet.isEmpty()) {
            throw new WalletNotFoundException("Wallet with id " + request.getWalletId() + " not found");
        }

        Transaction transaction = new Transaction();
        transaction.setName(request.getName());
        transaction.setType(transactionType.get());
        transaction.setAmount(request.getAmount());
        transaction.setCategory(category.get());
        transaction.setDate(convertToInstant(request.getDate()));
        transaction.setWallet(wallet.get());

        transactionRepository.save(transaction);

        ResTransactionDto response = new ResTransactionDto(
                transaction.getId(),
                convertToDateString(transaction.getDate()),
                transaction.getName(),
                new ResTransactionTypeDto(
                        transaction.getType().getId(),
                        transaction.getType().getName()
                ),
                transaction.getAmount(),
                new ResCategoryDto(
                        transaction.getCategory().getId(),
                        transaction.getCategory().getName()
                )
        );

        Map<String, Object> data = new HashMap<>();
        data.put(Constant.TRANSACTION, response);

        return BaseResponseDto.builder()
                .status(HttpStatus.OK)
                .description(Constant.SUCCESS)
                .data(data)
                .build();
    }

    @Override
    public BaseResponseDto getTransactionsByWalletId(Long walletId) {
        List<Transaction> transactions = transactionRepository.findByWalletIdAndDeletedAtIsNotNull(walletId);

        if (transactions.isEmpty()) {
            throw new TransactionNotFoundException("Transaction with wallet id " + walletId + " not found");
        }

        List<ResTransactionDto> responseList = transactions.stream()
                .map(transaction -> new ResTransactionDto(
                        transaction.getId(),
                        convertToDateString(transaction.getDate()),
                        transaction.getName(),
                        new ResTransactionTypeDto(
                                transaction.getType().getId(),
                                transaction.getType().getName()
                        ),
                        transaction.getAmount(),
                        new ResCategoryDto(
                                transaction.getCategory().getId(),
                                transaction.getCategory().getName()
                        )
                ))
                .toList();

        Map<String, Object> data = new HashMap<>();
        data.put(Constant.TRANSACTIONS, responseList);

        return BaseResponseDto.builder()
                .status(HttpStatus.OK)
                .description(Constant.SUCCESS)
                .data(data)
                .build();
    }

    @Override
    public BaseResponseDto update(Long id, ReqSaveTransactionDto request) {
        Optional<Transaction> existingTransactionOpt = transactionRepository.findByIdAndDeletedAtIsNull(id);
        if (existingTransactionOpt.isEmpty()) {
            throw new TransactionNotFoundException("Transaction with id " + id + " not found");
        }

        Transaction existingTransaction = existingTransactionOpt.get();

        Optional<TransactionType> transactionType = transactionTypeRepository.findActiveById(request.getTypeId().longValue());
        if (transactionType.isEmpty()) {
            throw new TransactionTypeNotFoundException("Transaction type with id " + request.getTypeId() + " not found");
        }

        Optional<Category> category = categoryRepository.findActiveById(request.getCategoryId().longValue());
        if (category.isEmpty()) {
            throw new CategoryNotFoundException("Category with id " + request.getCategoryId() + " not found");
        }

        Optional<Wallet> wallet = walletRepository.findActiveById(request.getWalletId().longValue());
        if (wallet.isEmpty()) {
            throw new WalletNotFoundException("Wallet with id " + request.getWalletId() + " not found");
        }

        existingTransaction.setName(request.getName());
        existingTransaction.setType(transactionType.get());
        existingTransaction.setAmount(request.getAmount());
        existingTransaction.setCategory(category.get());
        existingTransaction.setDate(convertToInstant(request.getDate()));
        existingTransaction.setWallet(wallet.get());

        transactionRepository.save(existingTransaction);

        ResTransactionDto response = new ResTransactionDto(
                existingTransaction.getId(),
                convertToDateString(existingTransaction.getDate()),
                existingTransaction.getName(),
                new ResTransactionTypeDto(
                        existingTransaction.getType().getId(),
                        existingTransaction.getType().getName()
                ),
                existingTransaction.getAmount(),
                new ResCategoryDto(
                        existingTransaction.getCategory().getId(),
                        existingTransaction.getCategory().getName()
                )
        );

        Map<String, Object> data = new HashMap<>();
        data.put(Constant.TRANSACTION, response);

        return BaseResponseDto.builder()
                .status(HttpStatus.OK)
                .description(Constant.SUCCESS)
                .data(data)
                .build();
    }

    @Override
    public BaseResponseDto delete(Long id) {
        Optional<Transaction> existingTransactionOpt = transactionRepository.findById(id);
        if (existingTransactionOpt.isEmpty()) {
            throw new TransactionNotFoundException("Transaction with id " + id + " not found");
        }

        transactionRepository.softDelete(id);

        Map<String, Object> data = new HashMap<>();

        return BaseResponseDto.builder()
                .status(HttpStatus.OK)
                .description(Constant.SUCCESS)
                .data(data)
                .build();
    }

    protected static Instant convertToInstant(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy", Locale.ENGLISH);

        LocalDate localDate = LocalDate.parse(date, formatter);

        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }

    protected static String convertToDateString(Instant instant) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy", Locale.ENGLISH);

        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        return localDateTime.format(formatter);
    }
}

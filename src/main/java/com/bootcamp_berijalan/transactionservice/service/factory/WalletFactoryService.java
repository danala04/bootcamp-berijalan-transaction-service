package com.bootcamp_berijalan.transactionservice.service.factory;

import com.bootcamp_berijalan.transactionservice.dto.response.ResTotalIncomeExpenseDto;
import com.bootcamp_berijalan.transactionservice.entity.Transaction;
import com.bootcamp_berijalan.transactionservice.exception.TransactionNotFoundException;
import com.bootcamp_berijalan.transactionservice.repository.TransactionRepository;
import com.bootcamp_berijalan.transactionservice.repository.WalletRepository;
import com.bootcamp_berijalan.transactionservice.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletFactoryService {
    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public ResTotalIncomeExpenseDto setTotalIncomeExpense(long walletId) {
        List<Transaction> transactions = transactionRepository.findByWalletIdAndDeletedAtIsNull(walletId);

        if (transactions.isEmpty()) {
            return new ResTotalIncomeExpenseDto(
                    0.0,
                    0.0,
                    0.0
            );
        }

        double totalIncome = 0;
        double totalExpense = 0;

        for (Transaction transaction : transactions) {
            if (transaction.getType().getId() == Constant.EXPENSE_ID) {
                totalExpense += transaction.getAmount();
            } else if (transaction.getType().getId() == Constant.INCOME_ID) {
                totalIncome += transaction.getAmount();
            }
        }

        return new ResTotalIncomeExpenseDto(
                totalIncome,
                totalExpense,
                totalIncome-totalExpense
        );
    }
}

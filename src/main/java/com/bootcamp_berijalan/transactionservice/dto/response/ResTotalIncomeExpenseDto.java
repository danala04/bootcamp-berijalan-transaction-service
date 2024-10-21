package com.bootcamp_berijalan.transactionservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResTotalIncomeExpenseDto {
    private double totalIncome;
    private double totalExpense;
    private double totalAmount;
}

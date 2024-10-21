package com.bootcamp_berijalan.transactionservice.repository;

import com.bootcamp_berijalan.transactionservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}

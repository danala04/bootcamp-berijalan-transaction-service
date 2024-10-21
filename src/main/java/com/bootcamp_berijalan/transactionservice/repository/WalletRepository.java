package com.bootcamp_berijalan.transactionservice.repository;

import com.bootcamp_berijalan.transactionservice.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}

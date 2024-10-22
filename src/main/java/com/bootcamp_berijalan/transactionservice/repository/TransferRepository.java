package com.bootcamp_berijalan.transactionservice.repository;

import com.bootcamp_berijalan.transactionservice.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}

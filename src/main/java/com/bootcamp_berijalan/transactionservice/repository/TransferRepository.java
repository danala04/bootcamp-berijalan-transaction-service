package com.bootcamp_berijalan.transactionservice.repository;

import com.bootcamp_berijalan.transactionservice.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
    public List<Transfer> findAllBySourceWalletIdOrDestinationWalletIdAndDeletedAtIsNull(Long sourceWalletId, Long destinationWalletId);
}

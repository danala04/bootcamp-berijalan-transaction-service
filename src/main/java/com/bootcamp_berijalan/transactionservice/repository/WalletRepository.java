package com.bootcamp_berijalan.transactionservice.repository;

import com.bootcamp_berijalan.transactionservice.entity.Wallet;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<List<Wallet>> findByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE Wallet w SET w.deletedAt = CURRENT_TIMESTAMP WHERE w.id = ?1")
    void softDelete(Long walletId);
}

package com.bootcamp_berijalan.transactionservice.repository;

import com.bootcamp_berijalan.transactionservice.entity.Wallet;
import com.bootcamp_berijalan.transactionservice.entity.WalletType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @Query("SELECT w FROM Wallet w WHERE w.userId = ?1 AND w.deletedAt IS NULL")
    List<Wallet> findActiveByUserId(Long userId);

    @Query("SELECT w FROM Wallet w WHERE w.id = ?1 AND w.deletedAt IS NULL")
    Optional<Wallet> findActiveById(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Wallet w SET w.deletedAt = CURRENT_TIMESTAMP WHERE w.id = ?1")
    void softDelete(Long walletId);
}

package com.bootcamp_berijalan.transactionservice.repository;

import com.bootcamp_berijalan.transactionservice.entity.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByWalletIdAndDeletedAtIsNull(long id);
    Optional<Transaction> findByIdAndDeletedAtIsNull(long id);

    @Transactional
    @Modifying
    @Query("UPDATE Transaction t SET t.deletedAt = CURRENT_TIMESTAMP WHERE t.id = ?1")
    void softDelete(Long id);
}

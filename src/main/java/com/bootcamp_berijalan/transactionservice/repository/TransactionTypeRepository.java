package com.bootcamp_berijalan.transactionservice.repository;

import com.bootcamp_berijalan.transactionservice.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {
    @Query("SELECT t FROM TransactionType t WHERE t.id = ?1 AND t.deletedAt IS NULL")
    Optional<TransactionType> findActiveById(Long id);

    List<TransactionType> findByDeletedAtIsNull();
}

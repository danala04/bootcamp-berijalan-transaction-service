package com.bootcamp_berijalan.transactionservice.repository;

import com.bootcamp_berijalan.transactionservice.entity.WalletType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WalletTypeRepository extends JpaRepository<WalletType, Long> {
    @Query("SELECT w FROM WalletType w WHERE w.id = ?1 AND w.deletedAt IS NULL")
    Optional<WalletType> findActiveById(Long id);
}

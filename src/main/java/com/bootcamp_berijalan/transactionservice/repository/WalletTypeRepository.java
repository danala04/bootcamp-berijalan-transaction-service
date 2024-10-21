package com.bootcamp_berijalan.transactionservice.repository;

import com.bootcamp_berijalan.transactionservice.entity.WalletType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletTypeRepository extends JpaRepository<WalletType, Long> {
    Optional<WalletType> findByName(String name);
}

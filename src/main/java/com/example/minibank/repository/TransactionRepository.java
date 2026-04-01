package com.example.minibank.repository;

import com.example.minibank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySourceAccountIdOrDestinationAccountIdOrderByTimestampDesc(Long sourceId, Long destId);
}

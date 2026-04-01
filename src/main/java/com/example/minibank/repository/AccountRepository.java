package com.example.minibank.repository;

import com.example.minibank.entity.Account;
import com.example.minibank.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByIban (String iban);
    List<Account> findByClientId(Long clientId);
}

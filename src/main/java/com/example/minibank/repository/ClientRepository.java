package com.example.minibank.repository;

import com.example.minibank.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client,Long> {
    boolean existsByNif ( String nif);

}

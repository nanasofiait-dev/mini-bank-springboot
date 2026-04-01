package com.example.minibank.service;

import com.example.minibank.dto.AccountDTO;
import com.example.minibank.entity.Account;
import com.example.minibank.entity.Client;
import com.example.minibank.exception.InactiveAccountException;
import com.example.minibank.exception.ResourceNotFoundException;
import com.example.minibank.repository.AccountRepository;
import com.example.minibank.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    public AccountService(AccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    private String generateIban() {
        String prefix = "PT50";
        java.security.SecureRandom random = new java.security.SecureRandom();
        StringBuilder digits = new StringBuilder();

        for (int i = 0; i < 21; i++) {
            digits.append(random.nextInt(10));
        }

        return prefix + digits.toString();
    }


    private String generateUniqueIban() {
        String iban;

        do {
            iban = generateIban();
        } while (accountRepository.existsByIban(iban));

        return iban;
    }

    // para fazer o metodo criar CONTA, foi preciso criar os dois metodos acima o primeiro gera o numeros do iban, e o segundo garante que seja unico se não for faz a crianção dos numeros novamente
    public Account criar(AccountDTO dto) {

        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        Account account = new Account();
        account.setAccountType(dto.getAccountType());
        account.setClient(client);
        account.setActive(true);
        account.setBalance(BigDecimal.ZERO);

        // gerar IBAN aqui
        account.setIban(generateUniqueIban());

        return accountRepository.save(account);
    }



    public Optional<Account> buscarPorId(Long id) {
        return accountRepository.findById(id);
    }



    public List<Account> listarPorCliente(Long clientId) {


        if (!clientRepository.existsById(clientId)) {
            throw new ResourceNotFoundException("Cliente não encontrado");
        }

        return accountRepository.findByClientId(clientId);
    }


    public Account desativarConta(Long id) {

        Account conta = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada"));

        if (conta.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new InactiveAccountException("Conta com saldo diferente de zero não pode ser desativada");
        }

        conta.setActive(false);

        return accountRepository.save(conta);
    }

}

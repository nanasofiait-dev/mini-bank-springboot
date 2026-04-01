package com.example.minibank.service;

import com.example.minibank.dto.TransactionDTO;
import com.example.minibank.entity.Account;
import com.example.minibank.entity.Transaction;
import com.example.minibank.exception.*;
import com.example.minibank.repository.AccountRepository;
import com.example.minibank.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private  final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public Transaction deposit(TransactionDTO dto) {

        // Buscar conta
        Account account = accountRepository.findById(dto.getSourceAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada"));

        // Verificar se está ativa
        if (!account.getActive()) {
            throw new InactiveAccountException("Conta está inativa");
        }

        // Atualizar saldo
        account.setBalance(account.getBalance().add(dto.getAmount()));

        // Criar transação
        Transaction transaction = new Transaction();
        transaction.setType(Transaction.Type.DEPOSIT);
        transaction.setAmount(dto.getAmount());
        transaction.setDescription(dto.getDescription());
        transaction.setSourceAccount(account);

        // Salvar alterações
        accountRepository.save(account);
        return transactionRepository.save(transaction);
    }

    public Transaction withdraw(TransactionDTO dto) {

        // Buscar conta
        Account account = accountRepository.findById(dto.getSourceAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada"));

        // Verificar se está ativa
        if (!account.getActive()) {
            throw new InactiveAccountException("Conta está inativa");
        }

        //validar o tipo
        if (dto.getType() != Transaction.Type.WITHDRAWAL){
            throw  new IllegalArgumentException("Tipo inválido");
        }

        //verifica se o saldo é suficiente
        if (account.getBalance().compareTo(BigDecimal.ZERO)<=0) {
            throw  new InsufficientFundsException("Saldo insuficiente");
        }

        // Subtrai o saldo e atualiza o saldo
        account.setBalance(account.getBalance().subtract(dto.getAmount()));

        // Criar transação
        Transaction transaction = new Transaction();
        transaction.setType(Transaction.Type.WITHDRAWAL);
        transaction.setAmount(dto.getAmount());
        transaction.setDescription(dto.getDescription());
        transaction.setSourceAccount(account);

        // Salvar alterações
        accountRepository.save(account);
        return transactionRepository.save(transaction);
    }

    public Transaction transfer(TransactionDTO dto) {
        //Validar tipo
        if (dto.getType() != Transaction.Type.TRANSFER){
            throw  new IllegalArgumentException("Tipo inválido");
        }

        // Buscar contas
        Account source = accountRepository.findById(dto.getSourceAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Conta origem não encontrada"));

        Account destination = accountRepository.findById(dto.getDestinationAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Conta destino não encontrada"));


       //Valido se contas são diferentes
        if (source.getId().equals(destination.getId())) {
            throw new DuplicateResourceException("Não é possível transferir para a mesma conta");
        }

        //Verifica se contas estão ativas
        if (!source.getActive() || !destination.getActive()){
            throw new InactiveAccountException("Conta inativa");
        }

        //verifica se o saldo é suficiente
        if (source.getBalance().compareTo(dto.getAmount())<0) {
            throw  new InsufficientFundsException("Saldo insuficiente");
        }

        // Subtrai o saldo e atualiza o saldo
        source.setBalance(source.getBalance().subtract(dto.getAmount()));
        destination.setBalance(destination.getBalance().add(dto.getAmount()));

        // Criar transação
        Transaction transaction = new Transaction();
        transaction.setType(Transaction.Type.TRANSFER);
        transaction.setAmount(dto.getAmount());
        transaction.setDescription(dto.getDescription());
        transaction.setSourceAccount(source);
        transaction.setDestinationAccount(destination);

        // Salvar alterações
        accountRepository.save(source);
        accountRepository.save(destination);
        return transactionRepository.save(transaction);
    }

    public List<Transaction> listarPorConta (Long accountId){

        if (! accountRepository.existsById(accountId)){
            throw new ResourceNotFoundException("Conta não encontrada");
        }

        return transactionRepository.findBySourceAccountIdOrDestinationAccountIdOrderByTimestampDesc(accountId,accountId);

     }


}

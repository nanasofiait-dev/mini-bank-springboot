package com.example.minibank.service;

import com.example.minibank.dto.TransactionDTO;
import com.example.minibank.entity.Account;
import com.example.minibank.entity.Client;
import com.example.minibank.entity.Transaction;
import com.example.minibank.exception.InactiveAccountException;
import com.example.minibank.repository.AccountRepository;
import com.example.minibank.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes de TransactionService")
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionService service;

    private Account sourceAccount;
    private Account destinationAccount;
    private Client client;
    private TransactionDTO depositDTO;
    private TransactionDTO withdrawDTO;
    private TransactionDTO transferDTO;
    private Transaction transaction;
    private Transaction transactionWithDrawal;
    private Transaction transactionTransfer;

    @BeforeEach
    public void setUp() {
        client = new Client("amanda@gmail.com", 1L, "Amanda Marques", "123456789", LocalDate.of(1994, 2, 11));

        sourceAccount = new Account(Account.AccountType.CHECKING, client);
        sourceAccount.setId(1L);
        sourceAccount.setBalance(BigDecimal.valueOf(100.00));
        sourceAccount.setActive(true);

        destinationAccount = new Account(Account.AccountType.CHECKING, client);
        destinationAccount.setId(2L);
        destinationAccount.setActive(true);

        depositDTO = new TransactionDTO(
                Transaction.Type.DEPOSIT,
                BigDecimal.valueOf(50.00),
                "Depósito teste",
                1L,
                null
        );

        withdrawDTO = new TransactionDTO(Transaction.Type.WITHDRAWAL,BigDecimal.valueOf(20),"Levantamento teste", 1L, null);

        transferDTO = new TransactionDTO(Transaction.Type.TRANSFER,BigDecimal.valueOf(10),"Transferencia teste",1L, 2L);

        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setType(Transaction.Type.DEPOSIT);
        transaction.setAmount(BigDecimal.valueOf(50.00));
        transaction.setSourceAccount(sourceAccount);


       transactionWithDrawal=new Transaction();
        transactionWithDrawal.setId(1L);
        transactionWithDrawal.setType(Transaction.Type.WITHDRAWAL);
        transactionWithDrawal.setAmount(BigDecimal.valueOf(30.00));
        transactionWithDrawal.setSourceAccount(sourceAccount);

        transactionTransfer=new Transaction();
        transactionTransfer.setId(1L);
        transactionTransfer.setType(Transaction.Type.TRANSFER);
        transactionTransfer.setAmount(BigDecimal.valueOf(20.00));
        transactionTransfer.setSourceAccount(sourceAccount);
    }

    @Test
    @DisplayName("Deve fazer depósito com sucesso")
    public void deveDepositar() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(sourceAccount));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction resultado = service.deposit(depositDTO);

        assertNotNull(resultado);
        assertEquals(Transaction.Type.DEPOSIT, resultado.getType());
        verify(accountRepository, times(1)).save(any(Account.class));
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Deve fazer levantamento com sucesso")
    public void deveLevantarDinheiro() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(sourceAccount));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transactionWithDrawal);

        Transaction resultado = service.withdraw(withdrawDTO);

        assertNotNull(resultado);
        assertEquals(Transaction.Type.WITHDRAWAL, resultado.getType());
        verify(accountRepository, times(1)).save(any(Account.class));
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Deve fazer transferência com sucesso")
    public void deveTransferir() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(destinationAccount));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transactionTransfer);

        Transaction resultado = service.transfer(transferDTO);

        assertNotNull(resultado);
        assertEquals(Transaction.Type.TRANSFER, resultado.getType());
        verify(accountRepository, times(2)).save(any(Account.class));
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando conta inativa no depósito")
    public void deveLancarExcecaoContaInativa() {
        sourceAccount.setActive(false); // desativa a conta

        when(accountRepository.findById(1L)).thenReturn(Optional.of(sourceAccount));

        assertThrows(InactiveAccountException.class, () -> service.deposit(depositDTO));
    }
}

package com.example.minibank.service;

import com.example.minibank.dto.AccountDTO;
import com.example.minibank.entity.Account;
import com.example.minibank.entity.Client;
import com.example.minibank.repository.AccountRepository;
import com.example.minibank.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes de AccountService")
public class AccountServiceTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private AccountRepository repository;

    @InjectMocks
    private AccountService service;
    private AccountDTO accountDTO;
    private Account account;
    private Client client;

    @BeforeEach
    public void setUp(){
        client= new Client("amanda@gmail.com",1L,"Amanda Marques", "123456789", LocalDate.of(1994,2,11));
        accountDTO=new AccountDTO(Account.AccountType.CHECKING, 1L);
        account=new Account(Account.AccountType.CHECKING,client);
        account.setId(1L);
    }

    @Test
    @DisplayName("Deve criar conta")
    public void deveCriarAccount(){
        when(clientRepository.findById(accountDTO.getClientId())).thenReturn(Optional.of(client));
        when(repository.save(any(Account.class))).thenReturn(account);

        Account resultado= service.criar(accountDTO);

        assertNotNull(resultado);
        assertEquals(Long.valueOf(1L), resultado.getId());
        assertEquals(Account.AccountType.CHECKING, accountDTO.getAccountType());

        verify(repository, times(1)).existsByIban(any(String.class));
        verify(repository, times(1)).save(any(Account.class));
    }

    @Test
    @DisplayName("Deve listar conta por cliente ")
    public void deveListarContaPorCliente(){

        List<Account> listaMock= new ArrayList<>(List.of(account));
        when(clientRepository.existsById(accountDTO.getClientId())).thenReturn(true);

        List<Account> resultado= service.listarPorCliente(Long.valueOf(1L));

        assertNotNull(resultado);
        verify(repository).findByClientId(Long.valueOf(1L));
    }

    @Test
    @DisplayName("Deve buscar conta por ID")
    public void deveBuscarContaPorId(){

        Optional<Account> optional=Optional.of(account);
        when(repository.findById(accountDTO.getClientId())).thenReturn(optional);

        Optional<Account> resultado= service.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        verify(repository, times(1)).findById(1L);

    }

    @Test
    @DisplayName("Deve desativar conta")
    public void deveDesativarConta(){
        when(repository.findById(accountDTO.getClientId())).thenReturn(Optional.of(account));
        when(repository.save(any(Account.class))).thenReturn(account);

        Account resultado= service.desativarConta(1L);

        assertNotNull(resultado);
        assertFalse(resultado.getActive());
    }
}

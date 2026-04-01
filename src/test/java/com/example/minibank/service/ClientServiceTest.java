package com.example.minibank.service;

import com.example.minibank.dto.ClientDTO;
import com.example.minibank.entity.Client;
import com.example.minibank.exception.DuplicateResourceException;
import com.example.minibank.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes de ClientService")
public class ClientServiceTest {

    @Mock
    private ClientRepository repository;

    @InjectMocks
    private ClientService service;
    private ClientDTO clientDTO;
    private Client client;

    @BeforeEach
   public void setUp(){
        clientDTO=new ClientDTO("Amanda Marques","amanda@gmail.com","123456789", LocalDate.of(1994,2,11));
        client= new Client("amanda@gmail.com",1L,"Amanda Marques", "123456789",LocalDate.of(1994,2,11));


    }

    @Test
    @DisplayName("Deve criar cliente com sucesso")
  public  void deveCriarClienteComSucesso(){
    when(repository.existsByNif(clientDTO.getNif())).thenReturn(false);

    when(repository.save(any(Client.class))).thenReturn(client);

    Client resultado= service.criar(clientDTO);

    assertNotNull(resultado);
    assertEquals(Long.valueOf(1L),  resultado.getId());
    assertEquals("Amanda Marques", resultado.getName());

    verify(repository, times(1)).existsByNif("123456789");
    verify(repository, times(1)).save(any(Client.class));
    }

    @Test
    @DisplayName("Deve lançar DuplicateResourceException quando Cliente já existe")
    public void deveLancarExceptionQuandoClienteDuplicado(){
        when(repository.existsByNif(clientDTO.getNif())).thenReturn(true);

        DuplicateResourceException exception= assertThrows(DuplicateResourceException.class,()->service.criar(clientDTO));

        assertEquals("Existe cliente com este nif", exception.getMessage());
        verify(repository, never()).save(any(Client.class));
    }

    @Test
    @DisplayName("Deve listar todos Cliente")
    public void deveListarTodosClientes(){

        List<Client> listaMock= new ArrayList<>( List.of(client));
        when(repository.findAll()).thenReturn(listaMock);

        List<Client> resultado= service.listarTodos();

        assertNotNull(resultado);
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Deve listar cliente por ID")
    public void deveListarClientePorId(){
        Optional<Client> optional=Optional.of(client);

        when(repository.findById(1L)).thenReturn(optional);

        Optional<Client> resultado=service.listarPorId(1L);

        assertTrue(resultado.isPresent());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve atualizar o cliente")
    public void deveAtualizarCliente(){
        Optional<Client> optional=Optional.of(client);

        when(repository.findById(1L)).thenReturn(optional);
        when(repository.save(any(Client.class))).thenReturn(client);

        ClientDTO dto= new ClientDTO("Amanda Forte","amanda@gmail.com","123456789", LocalDate.of(1994,2,11));

        Client resultado= service.atualizar(Long.valueOf(1L), dto);

        assertEquals(Long.valueOf(1L), resultado.getId());
        verify(repository).findById(1L);
        verify(repository).save(any(Client.class));
    }

    @Test
    @DisplayName("Deve deletar cliente com sucesso")
    public void deveDeletarClienteComSucesso(){
       when(repository.existsById(1L)).thenReturn(true);

       boolean resultado= service.delete(Long.valueOf(1L));

       assertTrue(resultado);
       verify(repository).existsById(Long.valueOf(1L));
       verify(repository).deleteById(Long.valueOf(1L));
    }


}

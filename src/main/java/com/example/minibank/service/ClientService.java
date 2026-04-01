package com.example.minibank.service;

import com.example.minibank.dto.ClientDTO;
import com.example.minibank.entity.Client;
import com.example.minibank.exception.DuplicateResourceException;
import com.example.minibank.exception.ResourceNotFoundException;
import com.example.minibank.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.DuplicateFormatFlagsException;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository repository;


    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public List<Client> listarTodos(){
        return repository.findAll();
    }

    public Optional<Client> listarPorId (Long id){
        return repository.findById(id);

    }

    public Client criar (ClientDTO dto){
        if (repository.existsByNif(dto.getNif())){
            throw new DuplicateResourceException("Existe cliente com este nif");
        }

        Client cliente= new Client();
        cliente.setName(dto.getName());
        cliente.setEmail(dto.getEmail());
        cliente.setNif(dto.getNif());
        cliente.setBirthDate(dto.getBirthDate());

        Client novo=repository.save(cliente);

        return novo;
    }

    public Client atualizar ( Long id, ClientDTO dto){

        Optional<Client> optional= repository.findById(id);

        if (optional.isEmpty()){
            throw new ResourceNotFoundException("Cliente não encontrado");
        }

        Client cliente= optional.get();
        cliente.setName(dto.getName());
        cliente.setEmail(dto.getEmail());
        cliente.setNif(dto.getNif());
        cliente.setBirthDate(dto.getBirthDate());

        Client atualizado= repository.save(cliente);

        return atualizado;

    }

    public  boolean delete (Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return true;
        }

        return false;
    }



}

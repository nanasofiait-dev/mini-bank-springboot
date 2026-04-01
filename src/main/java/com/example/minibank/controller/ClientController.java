package com.example.minibank.controller;

import com.example.minibank.dto.ClientDTO;
import com.example.minibank.entity.Client;
import com.example.minibank.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @Operation(summary = "Listar clientes", description = "Lista todos os clientes em lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<Client>> listarTodos() {
        List<Client> c = service.listarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(c);
    }

    @Operation(summary = "Buscar cliente por ID", description = "Retorna os detalhes de um cliente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado com o ID fornecido")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Client> buscarPorId(@PathVariable Long id) {
        return service.listarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar novo cliente", description = "Criar novo cliente no sistema")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Cliente criado com sucesso",
                     content = @Content(
                             mediaType = "application/json",
                             examples = @ExampleObject(
                                     value = """
                                             {
                                              "email": "miguel@gmail.com",
                                                 "id": 2,
                                                 "name": "Miguel Angelo",
                                                 "nif": "987654321",
                                                 "birthDate": "1996-06-10",
                                                 "createdAt": "2026-04-01T16:59:15.5056886"
                                               }
                                             """
                             )
                     )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de entrada inválidos",
                    content = @Content(
                            mediaType = "applications/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                            "error": "Bad Request",
                                            "message": "Erro de validação",
                                            "campos": {
                                            "name": "Nome é obrigatório/ Nome deve conter no mínimo 2 caracteres"
                                            "email": "Email é obrigatório/ Email inválido",
                                            "nif": " Nif é obrigatório/ Nif deve conter 9 dígitos",
                                            "birthDate": "Data de nascimento é obrigatório"
                                            },
                                            "status": 400
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Nif já existe",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                            "error": "Conflict",
                                            "message": "Existe cliente com este nif"
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<Client> criar(@Valid @RequestBody ClientDTO dto) {
        Client c = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(c);
    }

    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos (validação falhou)"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado com o ID fornecido")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Client> atualizar(@PathVariable Long id, @Valid @RequestBody ClientDTO dto) {
        Client c = service.atualizar(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(c);
    }

    @Operation(summary = "Apagar cliente", description = "Remove um cliente do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente apagado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado com o ID fornecido")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}

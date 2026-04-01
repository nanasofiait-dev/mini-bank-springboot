package com.example.minibank.controller;

import com.example.minibank.dto.AccountDTO;
import com.example.minibank.entity.Account;
import com.example.minibank.repository.AccountRepository;
import com.example.minibank.repository.ClientRepository;
import com.example.minibank.service.AccountService;
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
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;

    }

    @Operation(summary = "Buscar conta por ID", description = "Retorna os detalhes de uma conta específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conta encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrado com o ID fornecido")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Account>buscarPorId (@PathVariable Long id){
        return accountService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar conta por clienteId ", description = "Retorna os detalhes de uma conta específica de um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conta por cliente encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conta por cliente não encontrado com o ID fornecido")
    })
    @GetMapping("/client/{clienteId}")
    public ResponseEntity<List<Account>> listarPorCliente (@PathVariable Long clienteId){
        List<Account> accounts= accountService.listarPorCliente(clienteId);
        return ResponseEntity.status(HttpStatus.OK).body(accounts);
    }
    @Operation(summary = "Criar nova conta", description = "Cria um nova conta no sistema")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Conta criado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                             "accountType": "CHECKING",
                                               "clientId": 1
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de entrada inválidos",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                            "error": "Bad Request",
                                                            "message": "Erro de validação",
                                                            "campos": {
                                                              "clientId": "Cliente não encontrado"
                                                            },
                                                             "status": 400
                                                          }
                                            """
                            )

                    )

            )
    })
    @PostMapping
    public ResponseEntity<Account> criar (@Valid @RequestBody AccountDTO dto){
        Account a= accountService.criar(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(a);
    }

    @Operation(summary = "Desativar conta", description = "Desativa conta de um cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conta desativada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos (validação falhou)"),
            @ApiResponse(responseCode = "404", description = "ClienteId não encontrado com o ID fornecido")
    })
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Account> desativar (@PathVariable Long id){
        Account a= accountService.desativarConta(id);
        return ResponseEntity.status(HttpStatus.OK).body(a);
    }

}

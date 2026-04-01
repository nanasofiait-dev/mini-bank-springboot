package com.example.minibank.controller;

import com.example.minibank.dto.TransactionDTO;
import com.example.minibank.entity.Transaction;
import com.example.minibank.service.TransactionService;
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
@RequestMapping("/transactions")
public class TransactionController {

    private  final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }
    @Operation(summary = "Listar transação por conta", description = "Lista transação por conta todos os pacientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de transações por cliente retornada com sucesso")
    })
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Transaction>> listarPorConta(@PathVariable Long accountId){
        List<Transaction> t=service.listarPorConta(accountId);
        return ResponseEntity.status(HttpStatus.OK).body(t);
    }

    @Operation(summary = "Criar nova transação de depósito", description = "Cria um nova transação de depósito no sistema")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Transação criada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "type": "DEPOSIT",
                                                   "amount": 500,
                                                   "description": null,
                                                   "sourceAccount": {
                                                       "accountType": "CHECKING",
                                                       "client": {
                                                           "email": "amanda@gmail.com",
                                                           "id": 1,
                                                           "name": "Amanda Marques",
                                                           "nif": "123456789",
                                                           "birthDate": "1994-02-11",
                                                           "createdAt": "2026-04-01T16:11:11.565684"
                                                       },
                                                       "active": true,
                                                       "balance": 1000.00,
                                                       "createdAt": "2026-04-01T16:11:16.016924",
                                                       "iban": "PT50773523860095280216254",
                                                       "id": 1
                                                   },
                                                   "destinationAccount": null,
                                                   "id": 2,
                                                   "timestamp": "2026-04-01T16:47:08.9775798",
                                                   "validTransfer": true
                                               }
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
                                                             "clientId": "Conta não encontrada/ conta está inativa"
                                                            },
                                                             "status": 400
                                                          }
                                            """
                            )

                    )

            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Conta não encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                            "error": "Not Found",
                                            "message": "Conta não encontrada",
                                            "campos": {
                                            "clientId": "Conta não encontrada"
                                            },
                                            "status": 404
                                            }
                                            """
                            )
                    )
            )

    })
    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(@Valid@RequestBody TransactionDTO dto){
        Transaction t=service.deposit(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(t);
    }
    @Operation(summary = "Criar nova transação de levantamento", description = "Cria um nova transação de levantamento no sistema")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Transação criada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                {
                                                    "type": "WITHDRAWAL",
                                                    "amount": 500,
                                                    "description": null,
                                                    "sourceAccount": {
                                                        "accountType": "CHECKING",
                                                        "client": {
                                                            "email": "amanda@gmail.com",
                                                            "id": 1,
                                                            "name": "Amanda Marques",
                                                            "nif": "123456789",
                                                            "birthDate": "1994-02-11",
                                                            "createdAt": "2026-04-01T16:11:11.565684"
                                                        },
                                                        "active": true,
                                                        "balance": 500.00,
                                                        "createdAt": "2026-04-01T16:11:16.016924",
                                                        "iban": "PT50773523860095280216254",
                                                        "id": 1
                                                    },
                                                    "destinationAccount": null,
                                                    "id": 3,
                                                    "timestamp": "2026-04-01T16:56:07.0407866",
                                                    "validTransfer": true
                                                }
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
                                                             "clientId": "Conta não encontrada/ conta está inativa"
                                                            },
                                                             "status": 400
                                                          }
                                            """
                            )

                    )

            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Conta não encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                            "error": "Not Found",
                                            "message": "Conta não encontrada",
                                            "campos": {
                                            "clientId": "Conta não encontrada"
                                            },
                                            "status": 404
                                            }
                                            """
                            )
                    )
            )

    })
    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(@Valid@RequestBody TransactionDTO dto){
        Transaction t=service.withdraw(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(t);
    }
    @Operation(summary = "Criar nova transação de transferência", description = "Cria um nova transação de transferência no sistema")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Transação criada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                             {
                                                 "type": "TRANSFER",
                                                 "amount": 50,
                                                 "description": null,
                                                 "sourceAccount": {
                                                     "accountType": "CHECKING",
                                                     "client": {
                                                         "email": "amanda@gmail.com",
                                                         "id": 1,
                                                         "name": "Amanda Marques",
                                                         "nif": "123456789",
                                                         "birthDate": "1994-02-11",
                                                         "createdAt": "2026-04-01T16:11:11.565684"
                                                     },
                                                     "active": true,
                                                     "balance": 450.00,
                                                     "createdAt": "2026-04-01T16:11:16.016924",
                                                     "iban": "PT50773523860095280216254",
                                                     "id": 1
                                                 },
                                                 "destinationAccount": {
                                                     "accountType": "CHECKING",
                                                     "client": {
                                                         "email": "miguel@gmail.com",
                                                         "id": 2,
                                                         "name": "Miguel Angelo",
                                                         "nif": "987654321",
                                                         "birthDate": "1996-06-10",
                                                         "createdAt": "2026-04-01T16:59:15.505689"
                                                     },
                                                     "active": true,
                                                     "balance": 50.00,
                                                     "createdAt": "2026-04-01T16:59:24.641398",
                                                     "iban": "PT50927337809404029420950",
                                                     "id": 2
                                                 },
                                                 "id": 4,
                                                 "timestamp": "2026-04-01T16:59:28.4634087",
                                                 "validTransfer": true
                                             }
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
                                                             "clientId": "Conta não encontrada/ conta está inativa"
                                                            },
                                                             "status": 400
                                                          }
                                            """
                            )

                    )

            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Conta não encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                            "error": "Not Found",
                                            "message": "Conta não encontrada",
                                            "campos": {
                                            "clientId": "Conta não encontrada"
                                            },
                                            "status": 404
                                            }
                                            """
                            )
                    )
            )

    })
    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer(@Valid@RequestBody TransactionDTO dto){
        Transaction t=service.transfer(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(t);
    }
}

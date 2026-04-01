package com.example.minibank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Nome não pode ser nulo")
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, message = "Nome deve conter no mínimo 2 caracteres")
    private String name;

    @NotNull(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotNull(message = "Nif é obrigatório")
    @Column(unique = true,nullable = false)
    @Pattern(regexp = "^[0-9]{9}$", message = "Nif deve conter 9 dígitos")
    private String nif;

    @NotNull(message = "Data de nascimento é obrigatório")
    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate birthDate;

    @Column (updatable = false)
    private LocalDateTime createdAt;


    public Client() {
    }

    public Client(String email, Long id, String name, String nif, LocalDate birthDate) {
        this.email = email;
        this.id = id;
        this.name = name;
        this.nif = nif;
        this.birthDate = birthDate;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

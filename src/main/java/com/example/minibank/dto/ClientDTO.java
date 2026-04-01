package com.example.minibank.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class ClientDTO {
    @NotNull(message = "Nome não pode ser nulo")
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, message = "Nome deve conter no mínimo 2 caracteres")
    private String name;

    @NotNull(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotNull(message = "Nif é obrigatório")
    @Pattern(regexp = "^[0-9]{9}$", message = "Nif deve conter 9 dígitos")
    private String nif;

    @NotNull(message = "Data de nascimento é obrigatório")
    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate birthDate;

    public ClientDTO() {
    }

    public ClientDTO(String name, String email, String nif, LocalDate birthDate) {
        this.name = name;
        this.email = email;
        this.nif = nif;
        this.birthDate = birthDate;
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
}

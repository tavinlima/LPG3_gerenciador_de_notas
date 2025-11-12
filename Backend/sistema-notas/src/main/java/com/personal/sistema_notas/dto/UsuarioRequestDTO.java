package com.personal.sistema_notas.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UsuarioRequestDTO {
    @NotBlank
    private String perfil;

    @NotBlank
    @Size(max = 100)
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String senha;

    @NotNull
    private Integer statusId;

    @NotNull
    @Past
    private LocalDateTime dataNascimento;
}
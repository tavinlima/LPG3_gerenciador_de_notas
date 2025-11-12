package com.personal.sistema_notas.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {
    private Integer id;
    private String perfil;
    private String nome;
    private String email;
    private String status;
    private LocalDate dataNascimento;
}
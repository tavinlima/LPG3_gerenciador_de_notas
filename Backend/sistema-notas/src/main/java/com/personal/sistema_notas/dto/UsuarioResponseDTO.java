package com.personal.sistema_notas.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDateTime dataNascimento;
}
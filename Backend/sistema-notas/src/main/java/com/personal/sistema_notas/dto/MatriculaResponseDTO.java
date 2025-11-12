package com.personal.sistema_notas.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatriculaResponseDTO {
    private Integer id;
    private String alunoNome;
    private Integer alunoId;
    private String disciplinaNome;
    private Integer disciplinaId;
    private LocalDateTime dataMatricula;
    private String status;
}
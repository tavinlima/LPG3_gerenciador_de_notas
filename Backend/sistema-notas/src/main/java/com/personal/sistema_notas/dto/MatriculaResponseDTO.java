package com.personal.sistema_notas.dto;

import lombok.*;
import java.time.LocalDate;

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
    private LocalDate dataMatricula;
    private String status;
}
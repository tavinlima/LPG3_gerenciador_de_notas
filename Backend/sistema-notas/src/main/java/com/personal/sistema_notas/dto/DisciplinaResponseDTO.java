package com.personal.sistema_notas.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisciplinaResponseDTO {
    private Integer id;
    private String nome;
    private String professorNome;
    private Integer professorId;
    private BigDecimal cargaHoraria;
    private Integer numeroAulas;
    private String periodo;
}
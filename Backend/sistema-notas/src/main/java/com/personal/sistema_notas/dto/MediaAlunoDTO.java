package com.personal.sistema_notas.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaAlunoDTO {
    private Integer alunoId;
    private String alunoNome;
    private Integer disciplinaId;
    private String disciplinaNome;
    private BigDecimal mediaPonderada;
    private String status;
}
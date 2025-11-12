package com.personal.sistema_notas.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotaResponseDTO {
    private Integer id;
    private Integer matriculaId;
    private String alunoNome;
    private String disciplinaNome;
    private String tipoAvaliacao;
    private BigDecimal nota;
    private LocalDateTime dataLancamento;
    private BigDecimal peso;
}
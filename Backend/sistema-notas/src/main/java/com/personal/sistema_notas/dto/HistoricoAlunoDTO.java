package com.personal.sistema_notas.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoAlunoDTO {
    private Integer alunoId;
    private String alunoNome;
    private String alunoEmail;
    private List<DisciplinaHistoricoDTO> disciplinas;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DisciplinaHistoricoDTO {
        private String disciplinaNome;
        private String periodo;
        private String status;
        private BigDecimal mediaFinal;
        private List<NotaDetalheDTO> notas;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NotaDetalheDTO {
        private String tipoAvaliacao;
        private BigDecimal nota;
        private BigDecimal peso;
    }
}
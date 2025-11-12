package com.personal.sistema_notas.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class DisciplinaRequestDTO {
    @NotBlank
    @Size(max = 100)
    private String nome;

    @NotNull
    private Integer professorId;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal cargaHoraria;

    @NotNull
    @Min(1)
    private Integer numeroAulas;

    @NotBlank
    private String periodo;
}

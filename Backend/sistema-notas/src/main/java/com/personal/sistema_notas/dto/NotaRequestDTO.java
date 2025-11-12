package com.personal.sistema_notas.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class NotaRequestDTO {
    @NotNull
    private Integer matriculaId;

    @NotBlank
    private String tipoAvaliacao;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private BigDecimal nota;

    private LocalDateTime dataLancamento;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal peso;
}

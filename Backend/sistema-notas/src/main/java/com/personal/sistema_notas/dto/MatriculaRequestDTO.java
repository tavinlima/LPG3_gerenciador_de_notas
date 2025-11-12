package com.personal.sistema_notas.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MatriculaRequestDTO {
    @NotNull
    private Integer alunoId;

    @NotNull
    private Integer disciplinaId;

    private LocalDateTime dataMatricula;

    @NotNull
    private Integer statusId;
}
package com.personal.sistema_notas.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class MatriculaRequestDTO {
    @NotNull
    private Integer alunoId;

    @NotNull
    private Integer disciplinaId;

    private LocalDate dataMatricula;

    @NotNull
    private Integer statusId;
}
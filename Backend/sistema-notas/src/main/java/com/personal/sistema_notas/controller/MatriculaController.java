package com.personal.sistema_notas.controller;

import com.personal.sistema_notas.dto.MatriculaRequestDTO;
import com.personal.sistema_notas.dto.MatriculaResponseDTO;
import com.personal.sistema_notas.service.MatriculaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/matriculas")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Matrículas", description = "Gerenciamento de matrículas")
public class MatriculaController {

    private final MatriculaService matriculaService;

    @PostMapping
    @PreAuthorize("hasAnyRole('COORDENADOR', 'ALUNO')")
    @Operation(summary = "Criar matrícula")
    public ResponseEntity<MatriculaResponseDTO> criar(@Valid @RequestBody MatriculaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(matriculaService.criar(dto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('COORDENADOR', 'PROFESSOR', 'ALUNO')")
    @Operation(summary = "Buscar matrícula por ID")
    public ResponseEntity<MatriculaResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(matriculaService.buscarPorId(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('COORDENADOR', 'PROFESSOR')")
    @Operation(summary = "Listar todas as matrículas")
    public ResponseEntity<List<MatriculaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(matriculaService.listarTodas());
    }

    @GetMapping("/aluno/{alunoId}")
    @PreAuthorize("hasAnyRole('COORDENADOR', 'PROFESSOR', 'ALUNO')")
    @Operation(summary = "Listar matrículas de um aluno")
    public ResponseEntity<List<MatriculaResponseDTO>> listarPorAluno(@PathVariable Integer alunoId) {
        return ResponseEntity.ok(matriculaService.listarPorAluno(alunoId));
    }

    @GetMapping("/disciplina/{disciplinaId}")
    @PreAuthorize("hasAnyRole('COORDENADOR', 'PROFESSOR')")
    @Operation(summary = "Listar matrículas de uma disciplina")
    public ResponseEntity<List<MatriculaResponseDTO>> listarPorDisciplina(@PathVariable Integer disciplinaId) {
        return ResponseEntity.ok(matriculaService.listarPorDisciplina(disciplinaId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('COORDENADOR', 'PROFESSOR')")
    @Operation(summary = "Atualizar matrícula")
    public ResponseEntity<MatriculaResponseDTO> atualizar(@PathVariable Integer id,
                                                          @Valid @RequestBody MatriculaRequestDTO dto) {
        return ResponseEntity.ok(matriculaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    @Operation(summary = "Deletar matrícula")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        matriculaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

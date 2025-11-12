package com.personal.sistema_notas.controller;

import com.personal.sistema_notas.dto.*;
import com.personal.sistema_notas.service.NotaService;
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
@RequestMapping("/api/notas")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Notas", description = "Gerenciamento de notas")
public class NotaController {

    private final NotaService notaService;

    @PostMapping
    @PreAuthorize("hasAnyRole('COORDENADOR', 'PROFESSOR')")
    @Operation(summary = "Criar nota")
    public ResponseEntity<NotaResponseDTO> criar(@Valid @RequestBody NotaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notaService.criar(dto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('COORDENADOR', 'PROFESSOR', 'ALUNO')")
    @Operation(summary = "Buscar nota por ID")
    public ResponseEntity<NotaResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(notaService.buscarPorId(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('COORDENADOR', 'PROFESSOR')")
    @Operation(summary = "Listar todas as notas")
    public ResponseEntity<List<NotaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(notaService.listarTodas());
    }

    @GetMapping("/matricula/{matriculaId}")
    @PreAuthorize("hasAnyRole('COORDENADOR', 'PROFESSOR', 'ALUNO')")
    @Operation(summary = "Listar notas de uma matrícula")
    public ResponseEntity<List<NotaResponseDTO>> listarPorMatricula(@PathVariable Integer matriculaId) {
        return ResponseEntity.ok(notaService.listarPorMatricula(matriculaId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('COORDENADOR', 'PROFESSOR')")
    @Operation(summary = "Atualizar nota")
    public ResponseEntity<NotaResponseDTO> atualizar(@PathVariable Integer id,
                                                     @Valid @RequestBody NotaRequestDTO dto) {
        return ResponseEntity.ok(notaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('COORDENADOR', 'PROFESSOR')")
    @Operation(summary = "Deletar nota")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        notaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

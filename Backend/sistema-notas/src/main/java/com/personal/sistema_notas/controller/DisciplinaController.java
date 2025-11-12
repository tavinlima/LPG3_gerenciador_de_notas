package com.personal.sistema_notas.controller;

import com.personal.sistema_notas.dto.DisciplinaRequestDTO;
import com.personal.sistema_notas.dto.DisciplinaResponseDTO;
import com.personal.sistema_notas.dto.UsuarioResponseDTO;
import com.personal.sistema_notas.service.DisciplinaService;
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
@RequestMapping("/api/disciplinas")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Disciplinas", description = "Gerenciamento de disciplinas")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;
    private final MatriculaService matriculaService;

    @PostMapping
    @PreAuthorize("hasAnyRole('COORDENADOR', 'PROFESSOR')")
    @Operation(summary = "Criar disciplina")
    public ResponseEntity<DisciplinaResponseDTO> criar(@Valid @RequestBody DisciplinaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(disciplinaService.criar(dto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('COORDENADOR', 'PROFESSOR', 'ALUNO')")
    @Operation(summary = "Buscar disciplina por ID")
    public ResponseEntity<DisciplinaResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(disciplinaService.buscarPorId(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('COORDENADOR', 'PROFESSOR', 'ALUNO')")
    @Operation(summary = "Listar todas as disciplinas")
    public ResponseEntity<List<DisciplinaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(disciplinaService.listarTodas());
    }

    @GetMapping("/{id}/alunos")
    @PreAuthorize("hasAnyRole('COORDENADOR', 'PROFESSOR')")
    @Operation(summary = "Listar alunos matriculados na disciplina")
    public ResponseEntity<?> listarAlunosMatriculados(@PathVariable Integer id) {
        return ResponseEntity.ok(matriculaService.listarPorDisciplina(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('COORDENADOR', 'PROFESSOR')")
    @Operation(summary = "Atualizar disciplina")
    public ResponseEntity<DisciplinaResponseDTO> atualizar(@PathVariable Integer id,
                                                           @Valid @RequestBody DisciplinaRequestDTO dto) {
        return ResponseEntity.ok(disciplinaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    @Operation(summary = "Deletar disciplina")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        disciplinaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

package com.personal.sistema_notas.controller;

import com.itextpdf.text.DocumentException;
import com.personal.sistema_notas.dto.HistoricoAlunoDTO;
import com.personal.sistema_notas.dto.MediaAlunoDTO;
import com.personal.sistema_notas.service.DisciplinaService;
import com.personal.sistema_notas.service.NotaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Relatórios", description = "Relatórios e consultas especiais")
public class RelatorioController {

    private final NotaService notaService;
    private final DisciplinaService disciplinaService;

    @GetMapping("/alunos/{id}/historico")
    @PreAuthorize("hasAnyRole('COORDENADOR', 'PROFESSOR', 'ALUNO')")
    @Operation(summary = "Obter histórico completo do aluno")
    public ResponseEntity<HistoricoAlunoDTO> obterHistorico(@PathVariable Integer id) throws DocumentException, FileNotFoundException {
        return ResponseEntity.ok(notaService.obterHistorico(id));
    }

    @GetMapping("/alunos/{id}/media/{disciplinaId}")
    @PreAuthorize("hasAnyRole('COORDENADOR', 'PROFESSOR', 'ALUNO')")
    @Operation(summary = "Obter média de um aluno em uma disciplina")
    public ResponseEntity<MediaAlunoDTO> obterMedia(@PathVariable Integer id,
                                                    @PathVariable Integer disciplinaId) {
        return ResponseEntity.ok(notaService.obterMediaPorDisciplina(id, disciplinaId));
    }

    @GetMapping("/professores/{id}/disciplinas")
    @PreAuthorize("hasAnyRole('COORDENADOR', 'PROFESSOR')")
    @Operation(summary = "Listar disciplinas de um professor")
    public ResponseEntity<?> listarDisciplinasProfessor(@PathVariable Integer id) {
        return ResponseEntity.ok(disciplinaService.listarPorProfessor(id));
    }
}

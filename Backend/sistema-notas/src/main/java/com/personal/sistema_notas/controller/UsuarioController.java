package com.personal.sistema_notas.controller;

import com.personal.sistema_notas.domain.Usuario;
import com.personal.sistema_notas.dto.UsuarioRequestDTO;
import com.personal.sistema_notas.dto.UsuarioResponseDTO;
import com.personal.sistema_notas.repository.UsuarioRepository;
import com.personal.sistema_notas.service.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Validated
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> findAll() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @GetMapping("/perfil/{perfil}")
    @PreAuthorize("hasAnyRole('COORDENADOR', 'PROFESSOR')")
    public ResponseEntity<List<UsuarioResponseDTO>> getByPerfil(@RequestParam String perfil) {
        return ResponseEntity.ok(usuarioService.listarPorPerfil(perfil));
    }

    @GetMapping("/professores")
    @PreAuthorize("hasAnyRole('COORDENADOR', 'PROFESSOR', 'ALUNO')")
    public ResponseEntity<List<UsuarioResponseDTO>> getAllProfessores() {
        return ResponseEntity.ok(usuarioService.listarProfessores());
    }

    @GetMapping("/alunos")
    @PreAuthorize("hasAnyRole('COORDENADOR', 'PROFESSOR')")
    public ResponseEntity<List<UsuarioResponseDTO>> getAllAlunos() {
        return ResponseEntity.ok(usuarioService.listarAlunos());
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> create(@RequestBody UsuarioRequestDTO usuario) {
        UsuarioResponseDTO created = usuarioService.criar(usuario);

        return ResponseEntity.ok().body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> update(@PathVariable Integer id, @RequestBody UsuarioRequestDTO usuario) {
        return ResponseEntity.ok(usuarioService.atualizar(id, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

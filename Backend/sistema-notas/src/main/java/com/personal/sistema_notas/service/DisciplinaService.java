package com.personal.sistema_notas.service;

import com.personal.sistema_notas.domain.Disciplina;
import com.personal.sistema_notas.domain.Usuario;
import com.personal.sistema_notas.dto.DisciplinaRequestDTO;
import com.personal.sistema_notas.dto.DisciplinaResponseDTO;
import com.personal.sistema_notas.repository.DisciplinaRepository;
import com.personal.sistema_notas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public DisciplinaResponseDTO criar(DisciplinaRequestDTO dto) {
        Usuario professor = usuarioRepository.findById(dto.getProfessorId())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        if (!"Professor".equals(professor.getPerfil())) {
            throw new RuntimeException("Usuário não é um professor");
        }

        Disciplina disciplina = Disciplina.builder()
                .nome(dto.getNome())
                .professor(professor)
                .cargaHoraria(dto.getCargaHoraria())
                .numeroAulas(dto.getNumeroAulas())
                .periodo(dto.getPeriodo())
                .build();

        Disciplina salva = disciplinaRepository.save(disciplina);
        return toResponseDTO(salva);
    }

    public DisciplinaResponseDTO buscarPorId(Integer id) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));
        return toResponseDTO(disciplina);
    }

    public List<DisciplinaResponseDTO> listarTodas() {
        return disciplinaRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<DisciplinaResponseDTO> listarPorProfessor(Integer professorId) {
        return disciplinaRepository.findByProfessorId(professorId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public DisciplinaResponseDTO atualizar(Integer id, DisciplinaRequestDTO dto) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));

        Usuario professor = usuarioRepository.findById(dto.getProfessorId())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        disciplina.setNome(dto.getNome());
        disciplina.setProfessor(professor);
        disciplina.setCargaHoraria(dto.getCargaHoraria());
        disciplina.setNumeroAulas(dto.getNumeroAulas());
        disciplina.setPeriodo(dto.getPeriodo());

        Disciplina atualizada = disciplinaRepository.save(disciplina);
        return toResponseDTO(atualizada);
    }

    @Transactional
    public void deletar(Integer id) {
        if (!disciplinaRepository.existsById(id)) {
            throw new RuntimeException("Disciplina não encontrada");
        }
        disciplinaRepository.deleteById(id);
    }

    private DisciplinaResponseDTO toResponseDTO(Disciplina disciplina) {
        return DisciplinaResponseDTO.builder()
                .id(disciplina.getId())
                .nome(disciplina.getNome())
                .professorNome(disciplina.getProfessor().getNome())
                .professorId(disciplina.getProfessor().getId())
                .cargaHoraria(disciplina.getCargaHoraria())
                .numeroAulas(disciplina.getNumeroAulas())
                .periodo(disciplina.getPeriodo())
                .build();
    }
}
package com.personal.sistema_notas.service;

import com.personal.sistema_notas.dto.MatriculaRequestDTO;
import com.personal.sistema_notas.dto.MatriculaResponseDTO;
import com.personal.sistema_notas.domain.*;
import com.personal.sistema_notas.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final UsuarioRepository usuarioRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final StatusRepository statusRepository;

    @Autowired
    public MatriculaService(MatriculaRepository matriculaRepository,
                            UsuarioRepository usuarioRepository,
                            DisciplinaRepository disciplinaRepository,
                            StatusRepository statusRepository) {
        this.matriculaRepository = matriculaRepository;
        this.usuarioRepository = usuarioRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.statusRepository = statusRepository;
    }

    @Transactional
    public MatriculaResponseDTO criar(MatriculaRequestDTO dto) {
        Usuario aluno = usuarioRepository.findById(dto.getAlunoId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        if (!"Aluno".equals(aluno.getPerfil())) {
            throw new RuntimeException("Usuário não é um aluno");
        }

        Disciplina disciplina = disciplinaRepository.findById(dto.getDisciplinaId())
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));

        String periodoAtual = getPeriodoAtual();
        if (!disciplina.getPeriodo().equals(periodoAtual)) {
            throw new RuntimeException("Não é possível matricular em disciplina de período diferente do atual");
        }

        Status status = statusRepository.findById(dto.getStatusId())
                .orElseThrow(() -> new RuntimeException("Status não encontrado"));

        Matricula matricula = Matricula.builder()
                .aluno(aluno)
                .disciplina(disciplina)
                .data_matricula(dto.getDataMatricula() != null ? dto.getDataMatricula() : LocalDateTime.now())
                .status(status)
                .build();

        Matricula salva = matriculaRepository.save(matricula);
        return toResponseDTO(salva);
    }

    public MatriculaResponseDTO buscarPorId(Integer id) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matrícula não encontrada"));
        return toResponseDTO(matricula);
    }

    public List<MatriculaResponseDTO> listarTodas() {
        return matriculaRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<MatriculaResponseDTO> listarPorAluno(Integer alunoId) {
        return matriculaRepository.findByAlunoId(alunoId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<MatriculaResponseDTO> listarPorDisciplina(Integer disciplinaId) {
        return matriculaRepository.findByDisciplinaId(disciplinaId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public MatriculaResponseDTO atualizar(Integer id, MatriculaRequestDTO dto) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matrícula não encontrada"));

        Status status = statusRepository.findById(dto.getStatusId())
                .orElseThrow(() -> new RuntimeException("Status não encontrado"));

        matricula.setStatus(status);
        if (dto.getDataMatricula() != null) {
            matricula.setData_matricula(dto.getDataMatricula());
        }

        Matricula atualizada = matriculaRepository.save(matricula);
        return toResponseDTO(atualizada);
    }

    @Transactional
    public void deletar(Integer id) {
        if (!matriculaRepository.existsById(id)) {
            throw new RuntimeException("Matrícula não encontrada");
        }
        matriculaRepository.deleteById(id);
    }

    private String getPeriodoAtual() {
        int ano = LocalDate.now().getYear();
        int mes = LocalDate.now().getMonthValue();
        int semestre = mes <= 6 ? 1 : 2;
        return ano + "." + semestre;
    }

    private MatriculaResponseDTO toResponseDTO(Matricula matricula) {
        return MatriculaResponseDTO.builder()
                .id(matricula.getId())
                .alunoNome(matricula.getAluno().getNome())
                .alunoId(matricula.getAluno().getId())
                .disciplinaNome(matricula.getDisciplina().getNome())
                .disciplinaId(matricula.getDisciplina().getId())
                .dataMatricula(matricula.getData_matricula())
                .status(matricula.getStatus().getTitulo())
                .build();
    }
}

package com.personal.sistema_notas.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.personal.sistema_notas.dto.*;
import com.personal.sistema_notas.domain.Matricula;
import com.personal.sistema_notas.domain.Nota;
import com.personal.sistema_notas.repository.MatriculaRepository;
import com.personal.sistema_notas.repository.NotaRepository;
import com.personal.sistema_notas.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotaService {

    private final NotaRepository notaRepository;
    private final MatriculaRepository matriculaRepository;
    private final StatusRepository statusRepository;

    @Transactional
    public NotaResponseDTO criar(NotaRequestDTO dto) {
        Matricula matricula = matriculaRepository.findById(dto.getMatriculaId())
                .orElseThrow(() -> new RuntimeException("Matrícula não encontrada"));

        if(dto.getNota().compareTo(BigDecimal.valueOf(5.6)) >=0) {
            dto.setNota(BigDecimal.valueOf(6.0));
        }

        Nota nota = Nota.builder()
                .matricula(matricula)
                .tipoAvaliacao(dto.getTipoAvaliacao())
                .nota(dto.getNota())
                .dataLancamento(dto.getDataLancamento() != null ? dto.getDataLancamento() : LocalDateTime.now())
                .peso(dto.getPeso())
                .build();

        Nota salva = notaRepository.save(nota);
        return toResponseDTO(salva);
    }

    public NotaResponseDTO buscarPorId(Integer id) {
        Nota nota = notaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota não encontrada"));
        return toResponseDTO(nota);
    }

    public List<NotaResponseDTO> listarTodas() {
        return notaRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<NotaResponseDTO> listarPorMatricula(Integer matriculaId) {
        return notaRepository.findByMatriculaId(matriculaId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public NotaResponseDTO atualizar(Integer id, NotaRequestDTO dto) {
        Nota nota = notaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota não encontrada"));

        nota.setTipoAvaliacao(dto.getTipoAvaliacao());
        nota.setNota(dto.getNota());
        nota.setPeso(dto.getPeso());
        if (dto.getDataLancamento() != null) {
            nota.setDataLancamento(dto.getDataLancamento());
        }

        Nota atualizada = notaRepository.save(nota);
        return toResponseDTO(atualizada);
    }

    @Transactional
    public void deletar(Integer id) {
        if (!notaRepository.existsById(id)) {
            throw new RuntimeException("Nota não encontrada");
        }
        notaRepository.deleteById(id);
    }

    public HistoricoAlunoDTO obterHistorico(Integer alunoId) throws FileNotFoundException, DocumentException {
        List<Matricula> matriculas = matriculaRepository.findByAlunoId(alunoId);

        if (matriculas.isEmpty()) {
            throw new RuntimeException("Aluno não encontrado ou sem matrículas");
        }

        List<HistoricoAlunoDTO.DisciplinaHistoricoDTO> disciplinasHistorico = matriculas.stream()
                .map(matricula -> {
                    List<Nota> notas = notaRepository.findByMatriculaId(matricula.getId());
                    BigDecimal media = calcularMediaPonderada(notas);

                    if(media.compareTo(BigDecimal.valueOf(6)) <= 0 && media.compareTo(BigDecimal.valueOf(4)) >=0) {
                        matricula.setStatus(statusRepository.findByTitulo("recuperacao"));
                        matriculaRepository.save(matricula);
                    } else if (media.compareTo(BigDecimal.valueOf(4)) <= 0) {
                        matricula.setStatus(statusRepository.findByTitulo("reprovado"));
                        matriculaRepository.save(matricula);
                    }
                    else {
                        matricula.setStatus(statusRepository.findByTitulo("aprovado"));
                        matriculaRepository.save(matricula);
                    }

                    List<HistoricoAlunoDTO.NotaDetalheDTO> notasDetalhe = notas.stream()
                            .map(nota -> HistoricoAlunoDTO.NotaDetalheDTO.builder()
                                    .tipoAvaliacao(nota.getTipoAvaliacao())
                                    .nota(nota.getNota())
                                    .peso(nota.getPeso())
                                    .build())
                            .collect(Collectors.toList());

                    return HistoricoAlunoDTO.DisciplinaHistoricoDTO.builder()
                            .disciplinaNome(matricula.getDisciplina().getNome())
                            .periodo(matricula.getDisciplina().getPeriodo())
                            .status(matricula.getStatus().getTitulo())
                            .mediaFinal(media)
                            .notas(notasDetalhe)
                            .build();
                })
                .collect(Collectors.toList());

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("Relatório_aluno.pdf"));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("Relatório de alunos", font);

        HistoricoAlunoDTO aluno = HistoricoAlunoDTO.builder()
                .alunoId(alunoId)
                .alunoNome(matriculas.get(0).getAluno().getNome())
                .disciplinas(disciplinasHistorico)
                .build();

        Paragraph paragraph = new Paragraph(
                "Aluno: " + aluno.getAlunoNome() +
                "Disciplinas: " + aluno.getDisciplinas(), font);
        paragraph.setAlignment(Element.ALIGN_CENTER);


        document.add(chunk);
        document.add(paragraph);
        System.out.println("PDF criado com sucesso!" + document);
        document.close();

        return HistoricoAlunoDTO.builder()
                .alunoId(alunoId)
                .alunoNome(matriculas.get(0).getAluno().getNome())
                .disciplinas(disciplinasHistorico)
                .build();
    }

    public MediaAlunoDTO obterMediaPorDisciplina(Integer alunoId, Integer disciplinaId) {
        Matricula matricula = matriculaRepository.findByAlunoIdAndDisciplinaId(alunoId, disciplinaId)
                .orElseThrow(() -> new RuntimeException("Matrícula não encontrada"));

        List<Nota> notas = notaRepository.findByMatriculaId(matricula.getId());
        BigDecimal media = calcularMediaPonderada(notas);

        return MediaAlunoDTO.builder()
                .alunoId(alunoId)
                .alunoNome(matricula.getAluno().getNome())
                .disciplinaId(disciplinaId)
                .disciplinaNome(matricula.getDisciplina().getNome())
                .mediaPonderada(media)
                .status(matricula.getStatus().getTitulo())
                .build();
    }

    private BigDecimal calcularMediaPonderada(List<Nota> notas) {
        if (notas.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal somaNotasPonderadas = notas.stream()
                .map(nota -> nota.getNota().multiply(nota.getPeso()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal somaPesos = notas.stream()
                .map(Nota::getPeso)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (somaPesos.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return somaNotasPonderadas.divide(somaPesos, 2, RoundingMode.HALF_UP);
    }

    private NotaResponseDTO toResponseDTO(Nota nota) {
        return NotaResponseDTO.builder()
                .id(nota.getId())
                .matriculaId(nota.getMatricula().getId())
                .alunoNome(nota.getMatricula().getAluno().getNome())
                .disciplinaNome(nota.getMatricula().getDisciplina().getNome())
                .tipoAvaliacao(nota.getTipoAvaliacao())
                .nota(nota.getNota())
                .dataLancamento(nota.getDataLancamento())
                .peso(nota.getPeso())
                .build();
    }
}

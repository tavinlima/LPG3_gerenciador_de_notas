package com.personal.sistema_notas.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
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

                    if(media.compareTo(BigDecimal.valueOf(5.6)) >=0 && media.compareTo(BigDecimal.valueOf(6)) <=0)
                        media = (BigDecimal.valueOf(6.0));


                    if(media.compareTo(BigDecimal.valueOf(6)) < 0 && media.compareTo(BigDecimal.valueOf(4)) >=0) {
                        matricula.setStatus(statusRepository.findByTitulo("recuperacao"));
                    } else if (media.compareTo(BigDecimal.valueOf(4)) <= 0) {
                        matricula.setStatus(statusRepository.findByTitulo("reprovado"));
                    }
                    else {
                        matricula.setStatus(statusRepository.findByTitulo("aprovado"));
                    }
                    matriculaRepository.save(matricula);

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

        HistoricoAlunoDTO historico = HistoricoAlunoDTO.builder()
                .alunoId(alunoId)
                .alunoNome(matriculas.get(0).getAluno().getNome())
                .alunoEmail(matriculas.get(0).getAluno().getEmail())
                .disciplinas(disciplinasHistorico)
                .build();

        gerarPdf(historico);

        return HistoricoAlunoDTO.builder()
                .alunoId(alunoId)
                .alunoNome(matriculas.get(0).getAluno().getNome())
                .alunoEmail(matriculas.get(0).getAluno().getEmail())
                .disciplinas(disciplinasHistorico)
                .build();
    }

    public Document gerarPdf(HistoricoAlunoDTO historico) {
        Document document = new Document();
        try (FileOutputStream fos = new FileOutputStream("Relatório_aluno.pdf")) {
            PdfWriter.getInstance(document, fos);

            document.open();

            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Font textoFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

            Paragraph titulo = new Paragraph("Relatório de Aluno\n\n", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            document.add(new Paragraph("Aluno \n" +
                    "Nome: " +
                    historico.getAlunoNome() +
                    "\n" + "E-mail: " +
                    historico.getAlunoEmail() +
                    "\n", textoFont));

            for (HistoricoAlunoDTO.DisciplinaHistoricoDTO d : historico.getDisciplinas()) {
                Paragraph disciplinaTitulo = new Paragraph(
                        d.getDisciplinaNome() +
                                " | Período: " + d.getPeriodo() +
                                " | Média Final: " + d.getMediaFinal() +
                                " | Status: " + d.getStatus(),
                        textoFont
                );
                disciplinaTitulo.setSpacingBefore(10);
                disciplinaTitulo.setSpacingAfter(5);
                document.add(disciplinaTitulo);

                // ======= TABELA DE NOTAS =======
                PdfPTable tabela = new PdfPTable(3); // 3 colunas: Tipo, Nota, Peso
                tabela.setWidthPercentage(80);
                tabela.setWidths(new int[]{4, 2, 2});

                // Cabeçalhos
                PdfPCell header1 = new PdfPCell(new Phrase("Tipo de Avaliação", textoFont));
                PdfPCell header2 = new PdfPCell(new Phrase("Nota", textoFont));
                PdfPCell header3 = new PdfPCell(new Phrase("Peso", textoFont));

                BaseColor headerColor = new BaseColor(60, 120, 180);
                header1.setBackgroundColor(headerColor);
                header2.setBackgroundColor(headerColor);
                header3.setBackgroundColor(headerColor);

                header1.setHorizontalAlignment(Element.ALIGN_CENTER);
                header2.setHorizontalAlignment(Element.ALIGN_CENTER);
                header3.setHorizontalAlignment(Element.ALIGN_CENTER);

                tabela.addCell(header1);
                tabela.addCell(header2);
                tabela.addCell(header3);

                // Linhas de notas
                for (HistoricoAlunoDTO.NotaDetalheDTO nota : d.getNotas()) {
                    tabela.addCell(new PdfPCell(new Phrase(nota.getTipoAvaliacao(), textoFont)));
                    tabela.addCell(new PdfPCell(new Phrase(nota.getNota().toString(), textoFont)));
                    tabela.addCell(new PdfPCell(new Phrase(nota.getPeso().toString(), textoFont)));
                }

                document.add(tabela);
            }

            document.close();
            System.out.println("PDF criado com sucesso: Relatório_aluno.pdf");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return document;
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

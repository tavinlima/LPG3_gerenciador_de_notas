package com.personal.sistema_notas.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_disciplinas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @ManyToOne
    @JoinColumn(name="id_professor", nullable = false)
    private Usuario professor;

    @Column(name = "carga_horaria", nullable = false, precision = 5, scale = 2)
    private BigDecimal cargaHoraria;

    @Column(name="numero_aulas", nullable = false)
    private Integer numeroAulas;

    @Column(name="periodo", nullable = false, length = 6)
    private String periodo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id_disciplina) {
        this.id = id_disciplina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Usuario getProfessor() {
        return professor;
    }

    public void setProfessor(Usuario professorId) {
        this.professor = professorId;
    }

    public BigDecimal getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(BigDecimal cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Integer getNumeroAulas() {
        return numeroAulas;
    }

    public void setNumeroAulas(Integer numeroAulas) {
        this.numeroAulas = numeroAulas;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}

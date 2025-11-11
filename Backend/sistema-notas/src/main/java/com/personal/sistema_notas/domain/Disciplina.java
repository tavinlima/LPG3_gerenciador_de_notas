package com.personal.sistema_notas.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_disciplina")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_disciplina;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @ManyToOne
    @JoinColumn(name="id_usuario", nullable = false)
    private Usuario professorId;

    @Column(name = "carga_horaria", nullable = false, precision = 5, scale = 2)
    private BigDecimal cargaHoraria;

    @Column(name="numero_aulas", nullable = false)
    private Integer numeroAulas;

    @Column(name="periodo", nullable = false, length = 6)
    private String periodo;

    public Integer getId_disciplina() {
        return id_disciplina;
    }

    public void setId_disciplina(Integer id_disciplina) {
        this.id_disciplina = id_disciplina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Usuario getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Usuario professorId) {
        this.professorId = professorId;
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

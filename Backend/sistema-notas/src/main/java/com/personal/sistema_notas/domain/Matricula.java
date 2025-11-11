package com.personal.sistema_notas.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_matricula")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_matricula;

    @ManyToOne
    @JoinColumn(name = "id_aluno", nullable = false)
    private Usuario id_aluno;

    @ManyToOne
    @JoinColumn(name = "id_disciplina", nullable = false)
    private Disciplina id_disciplina;

    @ManyToOne
    @JoinColumn(name = "id_status", nullable = false)
    private Status id_status;

    @Column(name="data_matricula", nullable = false)
    private LocalDateTime data_matricula;

    public Integer getId_matricula() {
        return id_matricula;
    }

    public void setId_matricula(Integer id_matricula) {
        this.id_matricula = id_matricula;
    }

    public Usuario getId_aluno() {
        return id_aluno;
    }

    public void setId_aluno(Usuario id_aluno) {
        this.id_aluno = id_aluno;
    }

    public Disciplina getId_disciplina() {
        return id_disciplina;
    }

    public void setId_disciplina(Disciplina id_disciplina) {
        this.id_disciplina = id_disciplina;
    }

    public Status getId_status() {
        return id_status;
    }

    public void setId_status(Status id_status) {
        this.id_status = id_status;
    }

    public LocalDateTime getData_matricula() {
        return data_matricula;
    }

    public void setData_matricula(LocalDateTime data_matricula) {
        this.data_matricula = data_matricula;
    }
}

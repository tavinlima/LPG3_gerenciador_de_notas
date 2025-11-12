package com.personal.sistema_notas.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(name = "tb_matriculas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_aluno", nullable = false)
    private Usuario aluno;

    @ManyToOne
    @JoinColumn(name = "id_disciplina", nullable = false)
    private Disciplina disciplina;

    @ManyToOne
    @JoinColumn(name = "id_status", nullable = false)
    private Status status;

    @Column(name="data_matricula", nullable = false)
    private LocalDateTime data_matricula;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id_matricula) {
        this.id = id_matricula;
    }

    public Usuario getAluno() {
        return aluno;
    }

    public void setAluno(Usuario id_aluno) {
        this.aluno = id_aluno;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina id_disciplina) {
        this.disciplina = id_disciplina;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status id_status) {
        this.status = id_status;
    }

    public LocalDateTime getData_matricula() {
        return data_matricula;
    }

    public void setData_matricula(LocalDateTime data_matricula) {
        this.data_matricula = data_matricula;
    }
}

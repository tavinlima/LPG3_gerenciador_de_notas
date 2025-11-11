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
}

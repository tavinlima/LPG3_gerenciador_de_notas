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
    @JoinColumn(name="professor_id", nullable = false)
    private Usuario professorId;

    @Column(name = "carga_horaria", nullable = false, precision = 5, scale = 2)
    private BigDecimal cargaHoraria;

    @Column(name="numero_aulas", nullable = false)
    private Integer numeroAulas;

    @Column(name="periodo", nullable = false, length = 6)
    private String periodo;
}

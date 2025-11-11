package com.personal.sistema_notas.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_nota")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Nota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_nota;

    @ManyToOne
    @JoinColumn(name = "id_matricula", nullable = false)
    private Matricula id_matricula;

    @Column(name = "tipo_avaliacao", nullable = false, length = 50)
    private String tipoAvaliacao;

    @Column(name = "nota", nullable = false, precision = 4, scale = 2)
    private BigDecimal nota;

    @Column(name = "data_lancamento", nullable = false)
    private LocalDateTime dataLancamento;

    @Column(name = "peso", nullable = false, precision = 4, scale = 2)
    private BigDecimal peso = BigDecimal.valueOf(1.0);
}

package com.personal.sistema_notas.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_notas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Nota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_matricula", nullable = false)
    private Matricula matricula;

    @Column(name = "tipo_avaliacao", nullable = false, length = 50)
    private String tipoAvaliacao;

    @Column(name = "nota", nullable = false, precision = 4, scale = 2)
    private BigDecimal nota;

    @Column(name = "data_lancamento", nullable = false)
    private LocalDateTime dataLancamento;

    @Column(name = "peso", nullable = false, precision = 4, scale = 2)
    private BigDecimal peso = BigDecimal.valueOf(1.0);

    public Integer getId() {
        return id;
    }

    public void setId(Integer id_nota) {
        this.id = id_nota;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula id_matricula) {
        this.matricula = id_matricula;
    }

    public String getTipoAvaliacao() {
        return tipoAvaliacao;
    }

    public void setTipoAvaliacao(String tipoAvaliacao) {
        this.tipoAvaliacao = tipoAvaliacao;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }

    public LocalDateTime getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDateTime dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }
}

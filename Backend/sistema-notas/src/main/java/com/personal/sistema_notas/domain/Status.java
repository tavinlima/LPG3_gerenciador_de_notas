package com.personal.sistema_notas.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_status")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_status;

    @Column(name = "titulo", nullable = false, length = 15)
    private String titulo;

    public Integer getId_status() {
        return id_status;
    }

    public void setId_status(Integer id_status) {
        this.id_status = id_status;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}

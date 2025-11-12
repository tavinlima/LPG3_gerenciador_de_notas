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
@Builder
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "titulo", nullable = false, length = 15)
    private String titulo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id_status) {
        this.id = id_status;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}

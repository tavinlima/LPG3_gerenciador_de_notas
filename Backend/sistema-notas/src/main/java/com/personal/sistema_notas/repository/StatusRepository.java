package com.personal.sistema_notas.repository;

import com.personal.sistema_notas.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {
    Status findByTitulo(String titulo);
}

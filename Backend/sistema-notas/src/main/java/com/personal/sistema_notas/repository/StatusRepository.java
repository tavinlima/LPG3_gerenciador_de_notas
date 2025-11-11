package com.personal.sistema_notas.repository;

import com.personal.sistema_notas.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Integer> {
}

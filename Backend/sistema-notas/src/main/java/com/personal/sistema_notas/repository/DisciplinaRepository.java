package com.personal.sistema_notas.repository;

import com.personal.sistema_notas.domain.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Integer> {
    List<Disciplina> findByProfessorId(Integer professorId);
    List<Disciplina> findByPeriodo(String periodo);
}

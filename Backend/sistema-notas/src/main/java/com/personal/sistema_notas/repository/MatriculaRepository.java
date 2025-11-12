package com.personal.sistema_notas.repository;

import com.personal.sistema_notas.domain.Matricula;
import com.personal.sistema_notas.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface MatriculaRepository extends JpaRepository<Matricula, Integer> {
    List<Matricula> findByAlunoId(Integer alunoId);

    List<Matricula> findByDisciplinaId(Integer disciplinaId);

    @Query("SELECT m FROM Matricula m WHERE m.aluno.id = :alunoId AND m.disciplina.id = :disciplinaId")
    Optional<Matricula> findByAlunoIdAndDisciplinaId(@Param("alunoId") Integer alunoId,
                                                     @Param("disciplinaId") Integer disciplinaId);
}

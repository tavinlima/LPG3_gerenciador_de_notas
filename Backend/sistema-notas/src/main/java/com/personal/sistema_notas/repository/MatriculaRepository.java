package com.personal.sistema_notas.repository;

import com.personal.sistema_notas.domain.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface MatriculaRepository extends JpaRepository<Matricula, Integer> {
    List<Matricula> findByAlunoId(Integer alunoId);
    List<Matricula> findByDisciplinaId(Integer disciplinaId);

    @Query("SELECT m FROM Matricula m WHERE m.id_aluno.id_usuario = :alunoId AND m.id_disciplina.id_disciplina = :disciplinaId")
    Optional<Matricula> findByAlunoIdAndDisciplinaId(@Param("alunoId") Integer alunoId, @Param("disciplinaId") Integer disciplinaId);
}

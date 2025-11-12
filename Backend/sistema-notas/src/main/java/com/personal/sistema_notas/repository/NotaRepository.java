package com.personal.sistema_notas.repository;

import com.personal.sistema_notas.domain.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface NotaRepository extends JpaRepository<Nota, Integer> {
    List<Nota> findByMatriculaId(Integer matriculaId);

    @Query("SELECT n FROM Nota n WHERE n.id_matricula.id_aluno.id_usuario = :alunoId")
    List<Nota> findByAlunoId(@Param("alunoId") Integer alunoId);

    @Query("SELECT n FROM Nota n WHERE n.id_matricula.id_disciplina.id_disciplina = :disciplinaId")
    List<Nota> findByDisciplinaId(@Param("disciplinaId") Integer disciplinaId);
}
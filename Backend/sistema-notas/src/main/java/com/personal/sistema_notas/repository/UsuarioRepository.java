package com.personal.sistema_notas.repository;

import com.personal.sistema_notas.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByPerfil(String perfil);

    @Query("SELECT u FROM Usuario u WHERE u.perfil = 'Aluno'")
    List<Usuario> findAllAlunos();

    @Query("SELECT u FROM Usuario u WHERE u.perfil = 'Professor'")
    List<Usuario> findAllProfessores();
}

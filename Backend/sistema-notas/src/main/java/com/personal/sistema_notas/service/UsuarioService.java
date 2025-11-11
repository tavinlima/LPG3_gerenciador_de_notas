package com.personal.sistema_notas.service;

import com.personal.sistema_notas.domain.Usuario;
import com.personal.sistema_notas.repository.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<Usuario> listAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios;
    }

    public Usuario create(Usuario usuario) {
        Usuario user =  new Usuario();
        user.setNome(usuario.getNome());
        user.setEmail(usuario.getEmail());
        user.setSenha(usuario.getSenha());
        user.setPerfil(usuario.getPerfil());

        return usuarioRepository.save(user);
    }

    public Usuario update(Usuario usuario) {
        Usuario user = usuarioRepository.findById(usuario.getId()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setNome(usuario.getNome());
        user.setEmail(usuario.getEmail());
        user.setSenha(usuario.getSenha());
        user.setPerfil(usuario.getPerfil());

        return usuarioRepository.save(user);
    }

    public void delete(Integer id) {
        usuarioRepository.deleteById(id);
    }
}

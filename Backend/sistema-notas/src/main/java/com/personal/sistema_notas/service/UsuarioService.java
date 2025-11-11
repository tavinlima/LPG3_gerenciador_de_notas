package com.personal.sistema_notas.service;

import com.personal.sistema_notas.domain.Status;
import com.personal.sistema_notas.domain.Usuario;
import com.personal.sistema_notas.repository.StatusRepository;
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
    private final StatusRepository statusRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, StatusRepository statusRepository) {
        this.usuarioRepository = usuarioRepository;
        this.statusRepository = statusRepository;
    }

    @Transactional(readOnly = true)
    public List<Usuario> listAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios;
    }

    public Usuario create(Usuario usuario) {
       Status status =  statusRepository.findById(usuario.getStatus().getId_status()).orElseThrow(() -> new RuntimeException("Status não encontrado"));

        Usuario user =  new Usuario();
        user.setNome(usuario.getNome());
        user.setEmail(usuario.getEmail());
        user.setSenha(usuario.getSenha());
        user.setPerfil(usuario.getPerfil());
        user.setStatus(status);

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
    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
    public void delete(Integer id) {
        Usuario existing = findById(id);
        usuarioRepository.delete(existing);
    }
}

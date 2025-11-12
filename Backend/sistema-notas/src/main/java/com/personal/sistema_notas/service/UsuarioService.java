package com.personal.sistema_notas.service;

import com.personal.sistema_notas.domain.Status;
import com.personal.sistema_notas.domain.Usuario;
import com.personal.sistema_notas.dto.UsuarioRequestDTO;
import com.personal.sistema_notas.dto.UsuarioResponseDTO;
import com.personal.sistema_notas.repository.StatusRepository;
import com.personal.sistema_notas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final StatusRepository statusRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
        Status status = statusRepository.findById(dto.getStatusId())
                .orElseThrow(() -> new RuntimeException("Status não encontrado"));

        Usuario usuario = Usuario.builder()
                .perfil(dto.getPerfil())
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(passwordEncoder.encode(dto.getSenha()))
                .status(status)
                .dataNascimento(dto.getDataNascimento())
                .build();

        Usuario salvo = usuarioRepository.save(usuario);
        return toResponseDTO(salvo);
    }

    public UsuarioResponseDTO buscarPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return toResponseDTO(usuario);
    }

    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<UsuarioResponseDTO> listarPorPerfil(String perfil) {
        return usuarioRepository.findByPerfil(perfil).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public UsuarioResponseDTO atualizar(Integer id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Status status = statusRepository.findById(dto.getStatusId())
                .orElseThrow(() -> new RuntimeException("Status não encontrado"));

        usuario.setPerfil(dto.getPerfil());
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        }
        usuario.setStatus(status);
        usuario.setDataNascimento(dto.getDataNascimento());

        Usuario atualizado = usuarioRepository.save(usuario);
        return toResponseDTO(atualizado);
    }

    @Transactional
    public void deletar(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .perfil(usuario.getPerfil())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .status(usuario.getStatus().getTitulo())
                .dataNascimento(usuario.getDataNascimento())
                .build();
    }
}

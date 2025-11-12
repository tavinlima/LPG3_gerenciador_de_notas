package com.personal.sistema_notas.service;

import com.personal.sistema_notas.dto.LoginRequestDTO;
import com.personal.sistema_notas.dto.LoginResponseDTO;
import com.personal.sistema_notas.security.JwtTokenProvider;
import com.personal.sistema_notas.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getSenha()
                )
        );

        String token = tokenProvider.generateToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return LoginResponseDTO.builder()
                .token(token)
                .usuarioId(userDetails.getId())
                .nome(userDetails.getNome())
                .email(userDetails.getEmail())
                .perfil(userDetails.getPerfil())
                .build();
    }
}

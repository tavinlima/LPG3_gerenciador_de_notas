package com.personal.sistema_notas.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDTO {
    private String token;
    private String tipo = "Bearer";
    private Integer usuarioId;
    private String nome;
    private String email;
    private String perfil;
}

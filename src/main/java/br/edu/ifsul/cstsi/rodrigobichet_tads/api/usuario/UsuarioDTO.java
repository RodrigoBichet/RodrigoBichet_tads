package br.edu.ifsul.cstsi.rodrigobichet_tads.api.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO(


    @NotBlank
    String nome,

    @NotBlank
    String sobrenome,

    @Email @NotBlank
    String email,
    @NotBlank
    String senha) {
}

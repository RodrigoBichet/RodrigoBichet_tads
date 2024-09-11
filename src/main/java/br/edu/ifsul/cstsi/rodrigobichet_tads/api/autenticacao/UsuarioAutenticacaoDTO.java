package br.edu.ifsul.cstsi.rodrigobichet_tads.api.autenticacao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioAutenticacaoDTO(
    @Email @NotBlank
    String email,
    @NotBlank
    String senha) {
}

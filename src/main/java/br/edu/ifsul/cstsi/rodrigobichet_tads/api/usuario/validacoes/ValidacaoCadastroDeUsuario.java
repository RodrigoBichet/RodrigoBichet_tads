package br.edu.ifsul.cstsi.rodrigobichet_tads.api.usuario.validacoes;

import br.edu.ifsul.cstsi.rodrigobichet_tads.api.usuario.Usuario;

public interface ValidacaoCadastroDeUsuario {
    void validar(Usuario usuario);
}
package br.edu.ifsul.cstsi.rodrigobichet_tads.api.usuario.validacoes;


import br.edu.ifsul.cstsi.rodrigobichet_tads.api.infra.exception.ValidacaoEmailAindaNaoConfirmadoException;
import br.edu.ifsul.cstsi.rodrigobichet_tads.api.usuario.UsuarioDTO;
import br.edu.ifsul.cstsi.rodrigobichet_tads.api.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoEmailAindaNaoConfirmado implements ValidacaoLoginDoUsuario{
    @Autowired //indica ao Spring Boot que ele deve injetar essa dependência para a classe funcionar
    private UsuarioRepository rep;

    @Override
    public void validar(UsuarioDTO usuarioDTO) {
        if (!rep.findByEmail(usuarioDTO.email()).isConfirmado()){
            throw new ValidacaoEmailAindaNaoConfirmadoException("Erro: Este email ainda não foi confirmado. Favor acessar a caixa de email e clicar no link para confirmar.");
        }
    }
}
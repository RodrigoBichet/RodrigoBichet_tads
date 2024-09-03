package br.edu.ifsul.cstsi.rodrigobichet_tads.api.usuario;

import br.edu.ifsul.cstsi.rodrigobichet_tads.api.usuario.validacoes.ValidacaoCadastroDeUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service //indica que essa classe deve ser adicionada ao Contexto do aplicativo como um Bean da camada de serviço de dados
public class UsuarioService {

    @Autowired //indica ao Spring Boot que ele deve injetar essa dependência para a classe funcionar
    private UsuarioRepository rep;



    @Autowired //indica ao Spring Boot que ele deve injetar essa dependência para a classe funcionar
    private List<ValidacaoCadastroDeUsuario> validacoes;

    public Usuario insert(Usuario usuario) {
        Assert.isNull(usuario.getId(),"Não foi possível inserir o registro");

        //validações
        validacoes.forEach(v -> v.validar(usuario));

        var usuarioSalvo = rep.save(usuario);
        return usuarioSalvo;
    }
}
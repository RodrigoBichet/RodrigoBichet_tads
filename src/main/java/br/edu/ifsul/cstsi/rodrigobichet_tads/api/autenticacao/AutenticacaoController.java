package br.edu.ifsul.cstsi.rodrigobichet_tads.api.autenticacao;

import br.edu.ifsul.cstsi.rodrigobichet_tads.api.usuario.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager; //o gerenciador de autenticação é quem dispara o loadUserByUsername (isto é, é interno do Spring Security, tem que usar ele)

    @PostMapping
    public ResponseEntity efetuaLogin(@RequestBody UsuarioDTO userDTO){
        var authenticationDTO = new UsernamePasswordAuthenticationToken(userDTO.usuario(), userDTO.senha()); //converte o DTO em DTO do Spring Security
        manager.authenticate(authenticationDTO); //utiliza o genrenciador de autenticação para autenticar o userDTO
        return ResponseEntity.ok().build();
    }
}
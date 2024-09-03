//package br.edu.ifsul.cstsi.rodrigobichet_tads.api.autenticacao;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("api/v1/login")
//public class AutenticacaoController {
//
//    @Autowired
//    private AuthenticationManager manager; //o gerenciador de autenticação é quem dispara o loadUserByUsername (isto é, é interno do Spring Security, tem que usar ele)
//
//    @PostMapping
//    public ResponseEntity efetuaLogin(@RequestBody UsuarioDTO userDTO){
//        var authenticationDTO = new UsernamePasswordAuthenticationToken(userDTO.email(), userDTO.senha()); //converte o DTO em DTO do Spring Security
//        manager.authenticate(authenticationDTO); //utiliza o genrenciador de autenticação para autenticar o userDTO
//        return ResponseEntity.ok().build();
//    }
//}
package br.edu.ifsul.cstsi.rodrigobichet_tads.api.autenticacao;

import br.edu.ifsul.cstsi.rodrigobichet_tads.api.infra.security.TokenJwtDTO;
import br.edu.ifsul.cstsi.rodrigobichet_tads.api.infra.security.TokenService;
import br.edu.ifsul.cstsi.rodrigobichet_tads.api.usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController //indica que essa classe deve ser adicionada ao Contexto do aplicativo como um Bean da camada de controle API REST
@RequestMapping("api/v1/login") //Endpoint padrão da classe
public class AutenticacaoController {

    @Autowired //indica ao Spring Boot que ele deve injetar essa dependência para a classe funcionar
    private AuthenticationManager manager; //o gerenciador de autenticação é quem dispara o loadUserByUsername (isto é, é interno do Spring Security, tem que usar ele)

    @Autowired //indica ao Spring Boot que ele deve injetar essa dependência para a classe funcionar
    private TokenService tokenService;



    @PostMapping
    public ResponseEntity<TokenJwtDTO> efetuaLogin(@Valid @RequestBody UsuarioDTO data){
        var authenticationDTO = new UsernamePasswordAuthenticationToken(data.email(), data.senha()); //converte o DTO em DTO do Spring Security



        var authentication = manager.authenticate(authenticationDTO); //autentica o usuário (esse objeto contém o usuário e a senha)
        var tokenJWT = tokenService.geraToken((Usuario) authentication.getPrincipal()); //gera o token JWT para enviar na response
        return ResponseEntity.ok(new TokenJwtDTO(tokenJWT)); //envia a response com o token JWT
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
package br.edu.ifsul.cstsi.rodrigobichet_tads.api.motorista;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
    Pelo Princípio da Responsabilidade Única (SRP - Single-responsibility Principle) os controller manipulam
    apenas DTOs.
    Porém, se a meta for entregar o motorista no Curto Prazo, é aceitável não utilizar DTO no controller.
    Mas, se a meta for de Longo Prazo, a entrega de um motorista em produção, é importante aplicar o SRP, e fazer
    com que a controller trabalhe apenas com DTOs, isso facilitará a manutenção no futuro e ajuda na segurança do
    aplicativo (não expõe todos os dados aos clientes do aplicativo).
 */
@RestController //indica que essa classe deve ser adicionada ao Contexto do aplicativo como um Bean da camada de controle API REST
@RequestMapping("api/v1/motoristas") //Endpoint padrão da classe
public class MotoristaController {

    @Autowired //indica ao Spring Boot que ele deve injetar essa dependência para a classe funcionar
    private MotoristaService service;

    @GetMapping
    //O PageableDefault é sobrescrito pelos parâmetros da requisição (ou seja, a requisição é mandatória)
    //Experimente fazer a requisição assim: /api/v1/motoristas?size=2&sort=nome,desc (verá que sobrescreve o PageableDefault)
    public ResponseEntity<Page<MotoristaDTOResponse>> selectAll(@PageableDefault(size = 50, sort = "nome") Pageable paginacao) {
        return ResponseEntity.ok(service.getMotoristas(paginacao).map(MotoristaDTOResponse::new));
    }

    @GetMapping("{id}")
    public ResponseEntity<MotoristaDTOResponse> selectById(@PathVariable("id") Long id) {
        var p = service.getMotoristaById(id);
        if(p.isPresent()){
            return ResponseEntity.ok(new MotoristaDTOResponse(p.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<MotoristaDTOResponse>> selectByNome(@PathVariable("nome") String nome) {
        var motoristas = service.getMotoristasByNome(nome);
        return motoristas.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(motoristas.stream().map(MotoristaDTOResponse::new).collect(Collectors.toList()));
    }

    @PostMapping
    //@Secured({"ROLE_ADMIN"})
    public ResponseEntity<URI> insert(@RequestBody MotoristaDTOPost motoristaDTOPost, UriComponentsBuilder uriBuilder){
        var p = service.insert(new Motorista(
                null,
                motoristaDTOPost.nome(),
                motoristaDTOPost.email(),
                motoristaDTOPost.telefone(),
                1l,
                null
        ));
        var location = uriBuilder.path("api/v1/motoristas/{id}").buildAndExpand(p.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<MotoristaDTOResponse> update(@PathVariable("id") Long id, @Valid @RequestBody MotoristaDTOPut motoristaDTOPut){
        var p = service.update(new Motorista(
                id,
                motoristaDTOPut.nome(),
                motoristaDTOPut.email(),
                motoristaDTOPut.telefone(),
                1L,
                null

        ));
        return p != null ?
                ResponseEntity.ok(new MotoristaDTOResponse(p)):
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        return service.delete(id) ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
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
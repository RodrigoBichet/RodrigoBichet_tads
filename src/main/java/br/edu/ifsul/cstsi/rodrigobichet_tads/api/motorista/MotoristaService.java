package br.edu.ifsul.cstsi.rodrigobichet_tads.api.motorista;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/*
    Pelo Princípio da Responsabilidade Única (SRP - Single-responsibility Principle) as services manipulam
    apenas Entidades (objetos da classe de modelo).
 */
@Service //indica que essa classe deve ser adicionada ao Contexto do aplicativo como um Bean da camada de serviço de dados
public class MotoristaService {

    @Autowired //indica ao Spring Boot que ele deve injetar essa dependência para a classe funcionar
    private MotoristaRepository rep;

    public Page<Motorista> getMotoristas(Pageable paginacao) {
        return rep.findAll(paginacao);
    }

    public Optional<Motorista> getMotoristaById(Long id) {
        return rep.findById(id);
    }

    public List<Motorista> getMotoristasByNome(String nome) {
        return rep.findByNome(nome+"%");
    }

    public Motorista insert(Motorista motorista) {
        Assert.isNull(motorista.getId(),"Não foi possível inserir o registro");
        return rep.save(motorista);
    }

    public Motorista update(Motorista motorista) {
        Assert.notNull(motorista.getId(),"Não foi possível atualizar o registro");

        // Busca o motorista no banco de dados
        var optional = rep.findById(motorista.getId());
        if(optional.isPresent()) {
            var db = optional.get();
            // Copia as propriedades
            db.setNome(motorista.getNome());
            db.setTelefone(motorista.getTelefone());
            db.setEmail(motorista.getEmail());
            return rep.save(db);
        }
        return null;

    }

    public boolean delete(Long id) {
        var optional = rep.findById(id);
        if(optional.isPresent()) {
            rep.deleteById(id);
            return true;
        }
        return false;
    }
}
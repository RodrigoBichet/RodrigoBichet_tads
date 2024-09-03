package br.edu.ifsul.cstsi.rodrigobichet_tads.api.motorista;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MotoristaRepository extends JpaRepository<Motorista,Long> {

    @Query(value = "SELECT p FROM Motorista p where p.nome like ?1 order by p.nome")
    List<Motorista> findByNome(String nome);

}

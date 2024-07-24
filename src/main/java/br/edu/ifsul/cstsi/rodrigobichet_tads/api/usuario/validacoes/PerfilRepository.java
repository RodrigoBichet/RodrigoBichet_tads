package br.edu.ifsul.cstsi.rodrigobichet_tads.api.usuario.validacoes;

import br.edu.ifsul.cstsi.rodrigobichet_tads.api.usuario.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    Perfil findByNome(String nome);
}
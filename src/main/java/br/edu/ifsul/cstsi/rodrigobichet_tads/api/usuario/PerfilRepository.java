package br.edu.ifsul.cstsi.rodrigobichet_tads.api.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    Perfil findByNome(String nome);
}
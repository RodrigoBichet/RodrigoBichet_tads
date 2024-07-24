package br.edu.ifsul.cstsi.rodrigobichet_tads.api.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenConfirmacaoEmailRepository extends JpaRepository<TokenConfirmacaoEmail, Long> {
    TokenConfirmacaoEmail findByToken(String token);
}
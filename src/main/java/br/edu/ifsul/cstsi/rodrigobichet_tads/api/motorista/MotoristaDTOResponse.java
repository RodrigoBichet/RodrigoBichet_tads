package br.edu.ifsul.cstsi.rodrigobichet_tads.api.motorista;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record MotoristaDTOResponse(

        Long id,
        String nome,
        String email,
        String telefone


) {
    public MotoristaDTOResponse(Motorista motorista){
        this(motorista.getId(), motorista.getNome(), motorista.getEmail(), motorista.getTelefone());
    }
}

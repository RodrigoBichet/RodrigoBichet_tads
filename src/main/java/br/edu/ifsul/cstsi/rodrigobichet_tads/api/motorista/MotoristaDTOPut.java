package br.edu.ifsul.cstsi.rodrigobichet_tads.api.motorista;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record MotoristaDTOPut (
        @NotBlank(message = "O nome não pode ser nulo ou vazio") // Verifica se está vazio e estabelece como obrigatório (não pode ser nulo)
        @Size(min = 2, max = 50, message = "Tamanho mínimo de 2 e máximo de 50 caracteres")
        String nome,

        @NotBlank(message = "O email não pode ser nulo ou vazio") // Verifica se está vazio e estabelece como obrigatório (não pode ser nulo)
        @Size(min = 2, max = 255, message = "Tamanho mínimo de 2 e máximo de 255 caracteres")
        String email,

        @NotBlank(message = "O telefone não pode ser nulo ou vazio") // Verifica se está vazio e estabelece como obrigatório (não pode ser nulo)
        @Size(min = 2, max = 255, message = "Tamanho mínimo de 2 e máximo de 255 caracteres")
        String telefone

) {
    public MotoristaDTOPut(Motorista motorista){
            this(motorista.getNome(), motorista.getEmail(), motorista.getTelefone());
        }
    }
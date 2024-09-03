package br.edu.ifsul.cstsi.rodrigobichet_tads.api.motorista;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MotoristaDTOPost(

    @NotBlank(message = "O nome não pode ser nulo ou vazio") //verifica se está vazio e estabelece como obrigatório (não pode ser nulo)
    @Size(min = 2, max = 50, message = "Tamanho mínimo de 2 e máximo de 200")
    String nome,

    @NotBlank(message = "O email não pode ser nula ou vazio") //verifica se está vazio e estabelece como obrigatório (não pode ser nulo)
    @Email(message = "O email deve ser valido")
    String email,

    @NotNull
    String telefone

) {
    public MotoristaDTOPost(Motorista motorista){
        this(motorista.getNome(), motorista.getEmail(), motorista.getTelefone());
    }
}

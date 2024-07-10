package br.edu.ifsul.cstsi.rodrigobichet_tads.api.motorista;

import br.edu.ifsul.cstsi.rodrigobichet_tads.api.corrida.Corrida;
import br.edu.ifsul.cstsi.rodrigobichet_tads.api.veiculo.Veiculo;
import jakarta.persistence.*;

import java.util.Collection;
import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;


@Entity(name="Motorista")
@Table(name = "motoristas")
@Data
@NoArgsConstructor
@AllArgsConstructor

    public class Motorista {
        @GeneratedValue(
                strategy = GenerationType.IDENTITY
        )
        @Id

        private long id;

        private String nome;

        private String email;

        private String telefone;

        private long idVeiculo;
        @OneToMany(
                mappedBy = "motoristaByIdMotorista"
        )
        private Collection<Corrida> corridasById;
        @OneToMany(
                mappedBy = "motoristaByIdMotorista"
        )
        private Collection<Veiculo> veiculosById;
}

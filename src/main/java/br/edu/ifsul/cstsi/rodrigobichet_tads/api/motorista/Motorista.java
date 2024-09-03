package br.edu.ifsul.cstsi.rodrigobichet_tads.api.motorista;

import br.edu.ifsul.cstsi.rodrigobichet_tads.api.corrida.Corrida;
import br.edu.ifsul.cstsi.rodrigobichet_tads.api.veiculo.Veiculo;
import jakarta.persistence.*;

import java.util.Collection;

import lombok.*;


@Entity(name="Motorista")
@Table(name = "motoristas")
@Data
@AllArgsConstructor
@NoArgsConstructor
    public class Motorista {
        @GeneratedValue(
                strategy = GenerationType.IDENTITY
        )
        @Id
        @Column(
                name = "id"
        )
        private Long id;

        private String nome;

        private String email;

        private String telefone;
        @Basic
        @Column(
                name = "idVeiculo"
        )
        private Long idVeiculo;

        @OneToMany(
                mappedBy = "motoristaByIdMotorista"
        )
        private Collection<Corrida> corridasById;
}

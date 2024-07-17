package br.edu.ifsul.cstsi.rodrigobichet_tads.api.cliente;

import br.edu.ifsul.cstsi.rodrigobichet_tads.api.corrida.Corrida;
import jakarta.persistence.*;

import java.util.Collection;

import lombok.*;


@Entity(name="Cliente")
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor

    public class Cliente {
        @GeneratedValue(
                strategy = GenerationType.IDENTITY
        )
        @Id

        private long id;

        private String nome;

        private String email;

        private String telefone;
        @OneToMany(
                mappedBy = "clienteByIdCliente"
        )
        private Collection<Corrida> corridasById;
}

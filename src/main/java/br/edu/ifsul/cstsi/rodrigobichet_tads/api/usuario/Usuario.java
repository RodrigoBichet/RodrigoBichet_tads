package br.edu.ifsul.cstsi.rodrigobichet_tads.api.usuario;

import br.edu.ifsul.cstsi.rodrigobichet_tads.api.corrida.Corrida;
import jakarta.persistence.*;

import java.util.Collection;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;


@Entity(name="Usuario")
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor

    public class Usuario {
        @GeneratedValue(
                strategy = GenerationType.IDENTITY
        )
        @Id

        private long id;

        private String nome;

        private String email;

        private String telefone;
        @OneToMany(
                mappedBy = "usuarioByIdUsuario"
        )
        private Collection<Corrida> corridasById;
}

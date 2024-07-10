package br.edu.ifsul.cstsi.rodrigobichet_tads.api.corrida;

import br.edu.ifsul.cstsi.rodrigobichet_tads.api.motorista.Motorista;
import br.edu.ifsul.cstsi.rodrigobichet_tads.api.usuario.Usuario;
import jakarta.persistence.*;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;
import java.util.Collection;

@Entity(name="Corrida")
@Table(name = "corridas")
@Data
@NoArgsConstructor
@AllArgsConstructor

    public class Corrida {
        @GeneratedValue(
                strategy = GenerationType.IDENTITY
        )
        @Id

        private long id;


        private String tipoPagamento;


        private String detalhesPagamento;


        private LocalDate dataInicio;


        private double preco;
        @ManyToOne
        @JoinColumn(
                name = "id_motorista",
                referencedColumnName = "id",
                nullable = false
        )
        private Motorista motoristaByIdMotorista;
        @ManyToOne
        @JoinColumn(
                name = "id_usuario",
                referencedColumnName = "id",
                nullable = false
        )
        private Usuario usuarioByIdUsuario;
}

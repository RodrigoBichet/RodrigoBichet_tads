package br.edu.ifsul.cstsi.rodrigobichet_tads.api.veiculo;

import br.edu.ifsul.cstsi.rodrigobichet_tads.api.motorista.Motorista;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Collection;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;


@Entity(name="Veiculo")
@Table(name = "veiculos")
@Data
@NoArgsConstructor
@AllArgsConstructor

    public class Veiculo {
        @GeneratedValue(
                strategy = GenerationType.IDENTITY
        )
        @Id

        private long id;

        private String tipo;

        private String placa;

        private LocalDate anoFabricacao;
        @ManyToOne
        @JoinColumn(
                name = "id_motorista",
                referencedColumnName = "id",
                nullable = false
        )
        private Motorista motoristaByIdMotorista;
}

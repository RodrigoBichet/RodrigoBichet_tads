package br.edu.ifsul.cstsi.rodrigobichet_tads.api.corrida;

import br.edu.ifsul.cstsi.rodrigobichet_tads.api.motorista.Motorista;
import br.edu.ifsul.cstsi.rodrigobichet_tads.api.cliente.Cliente;
import jakarta.persistence.*;

import java.time.LocalDate;

import lombok.*;

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

        private Long id;


        private String tipoPagamento;


        private String detalhesPagamento;


        private LocalDate dataInicio;


        private Double preco;
        @ManyToOne
        @JoinColumn(
                name = "id_motorista",
                referencedColumnName = "id",
                nullable = false
        )
        private Motorista motoristaByIdMotorista;
        @ManyToOne
        @JoinColumn(
                name = "id_cliente",
                referencedColumnName = "id",
                nullable = false
        )
        private Cliente clienteByIdCliente;
}

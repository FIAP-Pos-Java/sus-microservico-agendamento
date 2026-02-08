package sus.microservico.agendamento.sus_microservico_agendamento.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sus.microservico.agendamento.sus_microservico_agendamento.model.enums.StatusFila;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_fila_espera")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilaEspera {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    private UUID pacienteId;
    private LocalDateTime dataInclusao;
    
    @Enumerated(EnumType.STRING)
    private StatusFila status;
    
    private Integer posicao;
}

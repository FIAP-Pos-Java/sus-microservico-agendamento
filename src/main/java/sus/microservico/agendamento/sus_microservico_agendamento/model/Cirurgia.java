package sus.microservico.agendamento.sus_microservico_agendamento.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sus.microservico.agendamento.sus_microservico_agendamento.model.enums.StatusCirurgia;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "tb_cirurgia")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cirurgia {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    private UUID pacienteId;
    private UUID medicoId;
    private LocalDate dataCirurgia;
    private LocalTime horaCirurgia;
    private String local;
    private String descricao;
    
    @Enumerated(EnumType.STRING)
    private StatusCirurgia status;
    
    private LocalDateTime dataAgendamento;
}

package sus.microservico.agendamento.sus_microservico_agendamento.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record CirurgiaDTO(
        @NotNull
        UUID pacienteId,
        
        @NotNull
        UUID medicoId,
        
        @NotNull
        LocalDate dataCirurgia,
        
        @NotNull
        LocalTime horaCirurgia,
        
        @NotBlank
        String local,
        
        String descricao
) {
}

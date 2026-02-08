package sus.microservico.agendamento.sus_microservico_agendamento.controller.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record FilaEsperaDTO(
        @NotNull
        UUID pacienteId
) {
}

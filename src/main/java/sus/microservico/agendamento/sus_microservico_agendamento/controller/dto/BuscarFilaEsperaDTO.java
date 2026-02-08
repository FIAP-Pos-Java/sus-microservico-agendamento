package sus.microservico.agendamento.sus_microservico_agendamento.controller.dto;

import sus.microservico.agendamento.sus_microservico_agendamento.model.enums.StatusFila;

import java.time.LocalDateTime;
import java.util.UUID;

public record BuscarFilaEsperaDTO(
        UUID id,
        UUID pacienteId,
        LocalDateTime dataInclusao,
        StatusFila status,
        Integer posicao
) {
}

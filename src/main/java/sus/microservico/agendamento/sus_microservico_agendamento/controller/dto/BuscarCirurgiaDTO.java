package sus.microservico.agendamento.sus_microservico_agendamento.controller.dto;

import sus.microservico.agendamento.sus_microservico_agendamento.model.enums.StatusCirurgia;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public record BuscarCirurgiaDTO(
        UUID id,
        UUID pacienteId,
        UUID medicoId,
        LocalDate dataCirurgia,
        LocalTime horaCirurgia,
        String local,
        String descricao,
        StatusCirurgia status,
        LocalDateTime dataAgendamento
) {
}

package sus.microservico.agendamento.sus_microservico_agendamento.client.dto;

import java.util.UUID;

public record BuscarMedicoDTO(
        UUID id,
        String nome,
        String email,
        String crm
) {
}

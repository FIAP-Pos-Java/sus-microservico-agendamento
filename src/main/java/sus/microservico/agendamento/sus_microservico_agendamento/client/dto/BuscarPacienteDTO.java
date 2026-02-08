package sus.microservico.agendamento.sus_microservico_agendamento.client.dto;

import java.time.LocalDate;
import java.util.UUID;

public record BuscarPacienteDTO(
        UUID id,
        String nome,
        String email,
        String cpf,
        String telefone,
        LocalDate dataNascimento
) {
}

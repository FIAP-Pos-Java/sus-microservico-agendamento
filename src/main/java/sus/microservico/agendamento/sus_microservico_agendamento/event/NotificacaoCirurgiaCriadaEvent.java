package sus.microservico.agendamento.sus_microservico_agendamento.event;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record NotificacaoCirurgiaCriadaEvent(
        UUID cirurgiaId,
        UUID pacienteId,
        UUID medicoId,
        LocalDate dataCirurgia,
        LocalTime horaCirurgia,
        String local
) implements Serializable {
}

package sus.microservico.agendamento.sus_microservico_agendamento.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum StatusCirurgia {
    AGENDADA,
    REALIZADA,
    CANCELADA;

    @JsonCreator
    public static StatusCirurgia fromValue(String value) {
        return StatusCirurgia.valueOf(value.toUpperCase());
    }
}

package sus.microservico.agendamento.sus_microservico_agendamento.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum StatusFila {
    AGUARDANDO,
    AGENDADA,
    CANCELADA;

    @JsonCreator
    public static StatusFila fromValue(String value) {
        return StatusFila.valueOf(value.toUpperCase());
    }
}

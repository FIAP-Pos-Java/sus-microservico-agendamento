package sus.microservico.agendamento.sus_microservico_agendamento.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    
    public static final String EXCHANGE = "sus.exchange";
    
    // Filas do agendamento
    public static final String CIRURGIA_CRIADA_QUEUE = "cirurgia.criada.queue";
    public static final String CIRURGIA_ATUALIZADA_QUEUE = "cirurgia.atualizada.queue";
    public static final String CIRURGIA_CANCELADA_QUEUE = "cirurgia.cancelada.queue";
    
    // Filas de notificação
    public static final String NOTIFICACAO_CIRURGIA_CRIADA_ROUTING_KEY = "notificacao.cirurgia.criada";
    public static final String NOTIFICACAO_CIRURGIA_ATUALIZADA_ROUTING_KEY = "notificacao.cirurgia.atualizada";
    public static final String NOTIFICACAO_CIRURGIA_CANCELADA_ROUTING_KEY = "notificacao.cirurgia.cancelada";

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

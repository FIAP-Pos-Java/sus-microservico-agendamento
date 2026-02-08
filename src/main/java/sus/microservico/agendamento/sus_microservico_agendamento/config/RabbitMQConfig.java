package sus.microservico.agendamento.sus_microservico_agendamento.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    
    public static final String EXCHANGE = "sus.exchange";
    public static final String CIRURGIA_AGENDADA_QUEUE = "cirurgia.agendada.queue";
    public static final String CIRURGIA_AGENDADA_ROUTING_KEY = "cirurgia.agendada";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue cirurgiaQueue() {
        return new Queue(CIRURGIA_AGENDADA_QUEUE, true);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(cirurgiaQueue())
                .to(exchange())
                .with(CIRURGIA_AGENDADA_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

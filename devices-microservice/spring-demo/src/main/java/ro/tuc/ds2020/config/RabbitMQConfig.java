package ro.tuc.ds2020.config;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String DEVICE_QUEUE = "device_queue";
    public static final String DEVICE_EXCHANGE = "device-exchange";
    public static final String DEVICE_ROUTING_KEY = "device-routing-key";

    @Bean
    public Queue deviceQueue() {
        return new Queue(DEVICE_QUEUE, true);
    }

    @Bean
    public DirectExchange deviceExchange() {
        return new DirectExchange(DEVICE_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue deviceQueue, DirectExchange deviceExchange) {
        return BindingBuilder.bind(deviceQueue).to(deviceExchange).with(DEVICE_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}

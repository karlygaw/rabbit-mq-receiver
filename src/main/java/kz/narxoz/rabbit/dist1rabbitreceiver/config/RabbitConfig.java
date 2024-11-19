package kz.narxoz.rabbit.dist1rabbitreceiver.config;

import com.rabbitmq.client.AMQP;
import kz.narxoz.rabbit.dist1rabbitreceiver.dto.Message;
import kz.narxoz.rabbit.dist1rabbitreceiver.dto.OrderDTO;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter();
        messageConverter.setClassMapper(classMapper());
        return messageConverter;
    }

    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("kz.narxoz.rabbit.dist1rabbitreceiver.dto.OrderDTO", OrderDTO.class);
        idClassMapping.put("kz.narxoz.rabbit.dist1rabbitreceiver.dto.Message", Message.class);
        classMapper.setIdClassMapping(idClassMapping);
        return classMapper;
    }

    @Bean
    public Queue deadLetterQueue(){
        return QueueBuilder.durable("department_messages_queue.dlq").build();
    }

    @Bean
    public TopicExchange deadLetterExchange(){
        return ExchangeBuilder.topicExchange("dlx").durable(true).build();
    }

    @Bean
    public Binding DLQBinding(){
        return BindingBuilder
                .bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with("dlx.department_queue");
    }
}

package kz.narxoz.rabbit.dist1rabbitreceiver.config;

import kz.narxoz.rabbit.dist1rabbitreceiver.dto.OrderDTO;
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
        classMapper.setIdClassMapping(idClassMapping);
        return classMapper;
    }
}

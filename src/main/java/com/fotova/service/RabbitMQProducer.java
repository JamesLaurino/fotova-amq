package com.fotova.service;

import com.fotova.config.RabbitMQConfig;
import com.fotova.dto.ProductDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void sendMessage(ProductDto productDto)
    {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "routing.key.test", productDto);
        System.out.println("Message envoyé : " + productDto.toString());
    }
}
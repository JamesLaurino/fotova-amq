package com.fotova.service;

import com.fotova.config.RabbitMQConfig;
import com.fotova.service.email.EmailService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private EmailService emailService;


    public void sendMessage(String orderId)
    {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "routing.key.test", orderId);
        emailService.sendOrderEmail(orderId);
        System.out.println("An order has been created and send by email : " + orderId);
    }
}
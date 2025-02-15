package com.fotova.service;


import com.fotova.config.RabbitMQConfig;
import com.fotova.dto.ProductDtoAmq;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DeadLetterQueueConsumer
{
    @RabbitListener(queues = RabbitMQConfig.DLQ_NAME)
    public void processFailedMessages(ProductDtoAmq productDto)
    {
        System.out.println("📌 Message dans la DLQ : ");
        System.out.println("Product Id : " + productDto.getId());
        System.out.println("Product Name : " + productDto.getName());
        System.out.println("Product Price : " + productDto.getPrice());
        System.out.println("Product Quantity : " + productDto.getQuantity());
    }
}
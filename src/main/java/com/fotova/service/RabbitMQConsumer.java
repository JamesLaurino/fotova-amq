package com.fotova.service;

import com.fotova.config.RabbitMQConfig;
import com.fotova.dto.ProductDto;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitMQConsumer
{
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME, ackMode = "MANUAL")
    public void receiveMessage(ProductDto productDto, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag)
    {
        try {
            if(productDto.getName().equals("error")) {
                throw new RuntimeException("Erreur simulée !");
            }
            System.out.println("Message received : !");
            System.out.println("Product Id : " + productDto.getId());
            System.out.println("Product Name : " + productDto.getName());
            System.out.println("Product Price : " + productDto.getPrice());
            System.out.println("Product Quantity : " + productDto.getQuantity());
            channel.basicAck(tag, false);

        } catch (Exception e)
        {
            System.out.println("🚨 Message rejeté et envoyé à la DLQ !");
            try {
                channel.basicNack(tag, false, false);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
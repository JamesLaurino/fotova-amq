package com.fotova.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendRegisterEmail(String uuid) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("test@example.com");
        message.setTo("destinataire@example.com");
        message.setSubject("Email send from Fotova-creation for register");
        message.setText("Click here : http://localhost:8080/api/v1/auth/register/check?uuid=" + uuid);

        mailSender.send(message);
    }

    public void sendOrderEmail(String orderId) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("test@example.com");
        message.setTo("destinataire@example.com");
        message.setSubject("Email send from Fotova-creation application");
        message.setText("An order has been created with the number : " + orderId);

        mailSender.send(message);
    }
}

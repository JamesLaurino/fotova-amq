package com.fotova.service.email;

import com.fotova.dto.ContactDtoAmq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.env.host}")
    private String HOST;

    @Value("${app.env.protocol}")
    private String PROTOCOL;

    @Value("${app.env.sender.email}")
    private String SENDER_EMAIL;;

    public void sendRegisterEmail(String uuid) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("test@example.com");
        message.setTo("destinataire@example.com");
        message.setSubject("Email send from Fotova-creation for register");
        message.setText("Click here : " + PROTOCOL + "://" + HOST + ":8080/api/v1/auth/register/check?uuid=" + uuid);

        mailSender.send(message);
    }

    public void sendEmailFromContact(ContactDtoAmq contactDtoAmq) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(contactDtoAmq.getEmail());
        message.setTo(SENDER_EMAIL);
        message.setSubject(contactDtoAmq.getSujet());
        message.setText("Message en provenance du user : " + contactDtoAmq.getNom() + ".\n" + contactDtoAmq.getMessage());
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

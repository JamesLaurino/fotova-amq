package com.fotova.service.email;

import com.fotova.dto.BillingDetailDtoAmq;
import com.fotova.dto.ContactDtoAmq;
import com.fotova.dto.ProductBillingDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
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

    public void sendRegisterEmail(String uuid,String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(SENDER_EMAIL);
        message.setTo(email);
        message.setSubject("Email send from Fotova-creation for register");
        message.setText("Click here : " + PROTOCOL + "://" + HOST + ":8080/api/v1/auth/register/check?uuid=" + uuid);

        mailSender.send(message);
    }

    public void sendResetPassword(String email,String uuidToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(SENDER_EMAIL);
        message.setTo(email);
        message.setSubject("Email send from Fotova-creation for reset password");
        message.setText("Click here to reset your password: " + PROTOCOL + "://" + HOST + ":8080/api/v1/auth/password-reset/check?uuid=" + uuidToken);

        mailSender.send(message);
    }

    public void sendBillingEmailMessage(BillingDetailDtoAmq billingDetailDtoAmq) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(SENDER_EMAIL);
        helper.setTo(billingDetailDtoAmq.getEmail());
        helper.setSubject("Congratulations and thank you for your order!");

        String htmlContent = buildHtmlContent(billingDetailDtoAmq);

        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

    private String buildHtmlContent(BillingDetailDtoAmq billingDetailDtoAmq) {

        StringBuilder html = new StringBuilder();

        html.append("<html>");
        html.append("<body style='font-family: Arial, sans-serif;'>");
        html.append("<h2>Thank you for your purchase!</h2>");
        html.append("<p>Order reference: <strong>")
                .append(billingDetailDtoAmq.getUUID())
                .append("</strong></p>");

        html.append("<table style='border-collapse: collapse; width: 100%;'>");
        html.append("<thead>");
        html.append("<tr style='background-color: #f2f2f2;'>");
        html.append("<th style='border: 1px solid #ddd; padding: 8px;'>Product</th>");
        html.append("<th style='border: 1px solid #ddd; padding: 8px;'>Price</th>");
        html.append("<th style='border: 1px solid #ddd; padding: 8px;'>Quantity</th>");
        html.append("<th style='border: 1px solid #ddd; padding: 8px;'>Total</th>");
        html.append("</tr>");
        html.append("</thead>");
        html.append("<tbody>");

        double grandTotal = 0.0;

        for (ProductBillingDto product : billingDetailDtoAmq.getProductBillingDtos()) {

            double total = product.getPrice() * product.getQuantity();
            grandTotal += total;

            html.append("<tr>");
            html.append("<td style='border: 1px solid #ddd; padding: 8px;'>")
                    .append(product.getProductName())
                    .append("</td>");
            html.append("<td style='border: 1px solid #ddd; padding: 8px;'>")
                    .append(String.format("%.2f €", product.getPrice()))
                    .append("</td>");
            html.append("<td style='border: 1px solid #ddd; padding: 8px;'>")
                    .append(product.getQuantity())
                    .append("</td>");
            html.append("<td style='border: 1px solid #ddd; padding: 8px;'>")
                    .append(String.format("%.2f €", total))
                    .append("</td>");
            html.append("</tr>");
        }

        html.append("</tbody>");
        html.append("</table>");

        html.append("<h3 style='text-align:right;'>Total: ")
                .append(String.format("%.2f €", grandTotal))
                .append("</h3>");

        html.append("<p>If you have any questions, feel free to contact us.</p>");
        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }

    public void sendEmailFromContact(ContactDtoAmq contactDtoAmq) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(contactDtoAmq.getEmail());
        message.setTo(SENDER_EMAIL);
        message.setSubject(contactDtoAmq.getSujet());
        message.setText("Message en provenance du user : " + contactDtoAmq.getNom() + ".\n" + contactDtoAmq.getMessage());
        mailSender.send(message);
    }

    /*
    * Mail for fotova in order to indicate that an order was made be someone
    *
    * */
    public void sendOrderEmail(String orderId) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(SENDER_EMAIL);
        message.setTo(SENDER_EMAIL);
        message.setSubject("Email send from Fotova-creation application");
        message.setText("An order has been created with the number : " + orderId);

        mailSender.send(message);
    }
}

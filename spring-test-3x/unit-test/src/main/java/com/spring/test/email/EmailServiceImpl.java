package com.spring.test.email;

import com.spring.test.model.Order;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailServiceImpl implements EmailService {
    private final String host;
    private final String senderEmail;
    private final String senderPassword;
    private String recipientEmail;
    private String subject;
    private String body;


    private boolean emailSent = false;

    public EmailServiceImpl(final EmailServiceBuilder emailServiceBuilder) {
        this.host = emailServiceBuilder.host;
        this.senderEmail = emailServiceBuilder.senderEmail;
        this.senderPassword = emailServiceBuilder.senderPassword;
    }

    public static EmailServiceBuilder builder() {
        return new EmailServiceBuilder();
    }

    @Override
    public void sendOrderConfirmation(Order order) {
        //prepare email content
        this.recipientEmail = order.getCustomer().getCustomerEmail();
        this.subject = order.getOrderId() + " for customer " + order.getCustomer().getCustomerName();
        this.body = "order-id:" + order.getOrderId() + "/n" +
                    "total:" + order.getTotal() + "/n" +
                    "order status:" + order.getStatus() +
                    "order items: /n" +
                    "- order item:" + order.getItems().get(0).getOrderItemId() +
                    "- product:" + order.getItems().get(0).getProductName();

        //prepare email structure
        Properties properties = new Properties();

        if(host.equals("smtp.gmail.com")) {
            properties.put("mail.smtp.auth", true);
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
        }else{
            properties.put("mail.smtp.auth", false);
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
        }

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email sent successfully!");

            emailSent = true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        emailSent = false;
    }

    @Override
    public boolean isEmailSent() {
        return this.emailSent;
    }

    public static class EmailServiceBuilder{
        private String host;
        private String senderEmail;
        private String senderPassword;

        public EmailServiceBuilder host(final String host) {
            this.host = host;
            return this;
        }

        public EmailServiceBuilder senderEmail(final String senderEmail) {
            this.senderEmail = senderEmail;
            return this;
        }

        public EmailServiceBuilder senderPassword(final String senderPassword) {
            this.senderPassword = senderPassword;
            return this;
        }

        public EmailServiceImpl build() {
            return new EmailServiceImpl(this);
        }
    }
}

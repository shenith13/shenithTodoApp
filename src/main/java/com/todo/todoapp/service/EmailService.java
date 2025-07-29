package com.todo.todoapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeEmail(String toEmail, String username) {
        try {
            logger.info("Preparing welcome email for {}", toEmail);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Welcome to Todo App");
            message.setText("Hi " + username + ",\n\nThank you for registering on our app!\n\n- Todo Team");

            mailSender.send(message);
            logger.info("Email sent successfully to {}", toEmail);
        } catch (Exception e) {
            logger.error("Failed to send email to {}: {}", toEmail, e.getMessage(), e);
        }
    }
}

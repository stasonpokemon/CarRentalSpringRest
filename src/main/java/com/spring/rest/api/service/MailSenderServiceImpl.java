package com.spring.rest.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {

    @Value("${spring.mail.username}")
    private String username;

    private final JavaMailSender mailSender;

    @Override
    public void send(String emailTo, String subject, String message) {

        log.info("Sending email to: {} with subject: {} with message: {}",
                emailTo, subject, message);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);

        log.info("Send email to: {} with subject: {} with message: {}",
                emailTo, subject, message);
    }
}

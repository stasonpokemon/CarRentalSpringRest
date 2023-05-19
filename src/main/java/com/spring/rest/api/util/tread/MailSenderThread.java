package com.spring.rest.api.util.tread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * MailSenderThread class.
 */
@Slf4j
public class MailSenderThread extends Thread {

    private final JavaMailSender mailSender;
    private final SimpleMailMessage message;

    public MailSenderThread(JavaMailSender mailSender, SimpleMailMessage message) {
        this.mailSender = mailSender;
        this.message = message;
    }

    @Override
    public void run() {
        mailSender.send(message);

        log.info("Send email to: {} with subject: {} with message: {}",
                message.getTo(), message.getSubject(), message.getText());
    }
}

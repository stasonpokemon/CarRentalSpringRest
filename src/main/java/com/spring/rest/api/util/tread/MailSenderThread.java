package com.spring.rest.api.util.tread;

import com.spring.rest.api.service.MailSenderService;

public class MailSenderThread extends Thread {

    private final MailSenderService mailSenderService;
    private final String emailTo;
    private final String subject;
    private final String message;

    public MailSenderThread(MailSenderService mailSenderService, String emailTo, String subject, String message) {
        this.mailSenderService = mailSenderService;
        this.emailTo = emailTo;
        this.subject = subject;
        this.message = message;
    }

    @Override
    public void run() {
        mailSenderService.send(emailTo, subject, message);
    }
}
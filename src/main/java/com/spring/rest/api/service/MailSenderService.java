package com.spring.rest.api.service;

public interface MailSenderService {

    void send(String emailTo, String subject, String message);
}

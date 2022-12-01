package com.spring.rest.api.service.impl;

import com.spring.rest.api.repo.ClientPassportRepository;
import com.spring.rest.api.service.ClientPassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientPassportServiceImpl implements ClientPassportService {

    @Autowired
    private ClientPassportRepository passportRepository;
}

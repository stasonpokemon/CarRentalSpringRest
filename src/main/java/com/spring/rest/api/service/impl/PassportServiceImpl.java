package com.spring.rest.api.service.impl;

import com.spring.rest.api.repo.PassportRepository;
import com.spring.rest.api.service.PassportService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Log4j2
public class PassportServiceImpl implements PassportService {

    @Autowired
    private PassportRepository passportRepository;

    @Autowired
    private ModelMapper modelMapper;



}

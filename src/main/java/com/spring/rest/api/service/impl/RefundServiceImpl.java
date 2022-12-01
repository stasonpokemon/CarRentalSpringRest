package com.spring.rest.api.service.impl;

import com.spring.rest.api.repo.RefundRepository;
import com.spring.rest.api.service.RefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefundServiceImpl implements RefundService {

    @Autowired
    private RefundRepository repository;
}

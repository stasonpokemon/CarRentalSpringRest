package com.spring.rest.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * ApplicationConfiguration configuration class.
 */
@EnableJpaRepositories("com.spring.rest.api.repo")
@ComponentScan("com.spring.rest.api")
@EnableTransactionManagement
@Configuration
public class ApplicationConfiguration {

}

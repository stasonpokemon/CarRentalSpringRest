package com.spring.rest.api.repo;

import com.spring.rest.api.entity.ClientPassport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientPassportRepository extends JpaRepository<ClientPassport, Long> {
}

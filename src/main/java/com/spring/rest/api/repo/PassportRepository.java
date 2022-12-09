package com.spring.rest.api.repo;

import com.spring.rest.api.entity.Passport;
import com.spring.rest.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassportRepository extends JpaRepository<Passport, Long> {

    Optional<Passport> findPassportByUser(User user);

}

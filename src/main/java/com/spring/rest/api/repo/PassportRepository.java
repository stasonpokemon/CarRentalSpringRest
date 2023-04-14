package com.spring.rest.api.repo;

import com.spring.rest.api.entity.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * JpaRepository, which works with Passport entity.
 */
@Repository
public interface PassportRepository extends JpaRepository<Passport, UUID> {

}

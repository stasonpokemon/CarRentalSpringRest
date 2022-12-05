package com.spring.rest.api.repo;

import com.spring.rest.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
    Optional<User>  findUserByEmail(String email);
    List<User> findAllByUsernameStartsWith(String usernameStartsWith);
    Optional<User>  findUserByActivationCode(String activationCode);
}

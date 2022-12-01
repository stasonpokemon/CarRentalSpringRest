package com.spring.rest.api.entity.dto;

import com.spring.rest.api.entity.ClientPassport;
import com.spring.rest.api.entity.Order;
import com.spring.rest.api.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String username;

    private String password;

    private String email;

    private String activationCode;

    private Set<Role> roles;

    private ClientPassport passport;

    private List<Order> orders = new ArrayList<>();


}

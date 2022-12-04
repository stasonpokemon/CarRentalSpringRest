package com.spring.rest.api.entity.dto;

import com.spring.rest.api.entity.ClientPassport;
import com.spring.rest.api.entity.Order;
import com.spring.rest.api.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    //    @NotBlank(message = "Please fill the username")
    @Min(value = 3, message = "Username must be more than 3 symbols")
    private String username;

    //    @NotBlank(message = "Please fill the password")
    @Min(value = 6, message = "Password must be more than 6 symbols")
    private String password;

    @NotBlank(message = "Please fill the email")
    @Email(message = "Please fill the correct email")
    private String email;

    private String activationCode;

    private Set<Role> roles;

    private ClientPassport passport;

    private List<Order> orders = new ArrayList<>();


}

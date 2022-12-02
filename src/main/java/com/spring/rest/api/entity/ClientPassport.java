package com.spring.rest.api.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "passports")
@Data
public class ClientPassport extends BaseEntity{

    @NotBlank(message = "Please fill the name")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Please fill the surname")
    @Column(name = "surname")
    private String surname;

    @NotBlank(message = "Please fill the patronymic")
    @Column(name = "patronymic")
    private String patronymic;

    @NotBlank(message = "Please fill the birthday")
    @Column(name = "birthday")
    private Date birthday;

    @NotBlank(message = "Please fill the address")
    @Column(name = "address")
    private String address;




}
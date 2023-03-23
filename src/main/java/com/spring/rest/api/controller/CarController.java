package com.spring.rest.api.controller;

import com.spring.rest.api.entity.dto.CarDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequestMapping("/cars")
public interface CarController {

    @GetMapping("/all")
    ResponseEntity<?> findAll(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable);

    @GetMapping
    ResponseEntity<?> findAllNotMarkedAsDeleted(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable);

    @GetMapping("/{id}")
    ResponseEntity<?> findCar(
            @PathVariable(name = "id") Long carId);

    @PostMapping
    ResponseEntity<?> createCar(
            @RequestBody @Valid CarDTO carDTO);

    @PatchMapping("/{id}")
    ResponseEntity<?> updateCar(
            @PathVariable("id") Long carId,
            @RequestBody @Valid CarDTO carDTO);

    @PatchMapping("/{id}/fix")
    ResponseEntity<?> fixBrokenCar(
            @PathVariable("id") Long carId);

    @PatchMapping("/{id}/remove")
    ResponseEntity<String> markCarAsDeleted(
            @PathVariable("id") Long carId);
}

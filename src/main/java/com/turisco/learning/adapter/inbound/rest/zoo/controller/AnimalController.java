package com.turisco.learning.adapter.inbound.rest.zoo.controller;

import com.turisco.learning.adapter.inbound.rest.zoo.dto.AnimalDTO;
import com.turisco.learning.adapter.inbound.rest.zoo.mapper.AnimalDTOMapper;
import com.turisco.learning.domain.exception.InvalidAnimalException;
import com.turisco.learning.adapter.outbound.persistence.entity.AnimalAttributeInterface;
import com.turisco.learning.domain.service.AnimalService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/zoo")
@RequiredArgsConstructor
@Slf4j
public class AnimalController {

    private final AnimalDTOMapper mapper;
    private final AnimalService service;

    @GetMapping
    @RolesAllowed("USER")
    public ResponseEntity<Map<String, Object>> exposeAllAnimals() {
        List<AnimalAttributeInterface> animals = service.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put("total", animals.size());
        response.put("data", animals);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<Object> exposeCreateAnimal(@Valid @RequestBody AnimalDTO animalDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, Object> errors = new HashMap<>();
            for (ObjectError error : result.getAllErrors()) {
                errors.put(error.getObjectName(), error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        try {
            AnimalAttributeInterface animal = service.create(animalDTO);
            log.error("Animal Created Successfully!");
            return ResponseEntity.status(HttpStatus.CREATED).body(animal);
        } catch (InvalidAnimalException e) {
            log.error("Invalid Animal!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}

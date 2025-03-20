package com.turisco.learning.controller;

import com.turisco.learning.dto.AnimalDTO;
import com.turisco.learning.model.Animal;
import com.turisco.learning.repository.AnimalRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/animals")
public class AnimalController {

    private final AnimalMapper mapper;
    private final AnimalRepository repository;

    public AnimalController(AnimalMapper mapper, AnimalRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> exposeAllAnimals() {
        List<Animal> animals = repository.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put("total", animals.size());
        response.put("data", animals);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<Object> exposeCreateAnimal(@Valid @RequestBody AnimalDTO animal, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, Object> errors = new HashMap<>();
            for (ObjectError error : result.getAllErrors()) {
                errors.put(error.getObjectName(), error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        var target = mapper.toModel(animal);
        Animal persisted = repository.save(target);
        return ResponseEntity.status(HttpStatus.CREATED).body(persisted);
    }
}

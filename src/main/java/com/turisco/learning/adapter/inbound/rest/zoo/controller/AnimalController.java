package com.turisco.learning.adapter.inbound.rest.zoo.controller;

import com.turisco.learning.adapter.inbound.rest.zoo.dto.AnimalDTO;
import com.turisco.learning.adapter.outbound.persistence.entity.AnimalAttributeInterface;
import com.turisco.learning.domain.exception.InvalidAnimalException;
import com.turisco.learning.domain.service.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/zoo")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Animal Administration", description = "Endpoints to work with Animal Registration")
public class AnimalController {

    private final AnimalService service;

    @Operation(summary = "List all animals", description = "Return a list of animals")
    @GetMapping
    @RolesAllowed({"USER", "ADMIN"})
    public ResponseEntity<Map<String, Object>> exposeAllAnimals() {
        List<AnimalAttributeInterface> animals = service.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put("total", animals.size());
        response.put("data", animals);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create an animal", description = "Create an animal and classify the species by name")
    @PostMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<Object> exposeCreateAnimal(@Valid @RequestBody AnimalDTO animalDTO,
                                                     BindingResult result) {
        if (result.hasErrors()) {
            Map<String, Object> errors = new HashMap<>();
            for (ObjectError error : result.getAllErrors()) {
                errors.put(error.getObjectName(), error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        try {
            service.generateSpeciesName(animalDTO);
            AnimalAttributeInterface animal = service.create(animalDTO);
            log.info("Animal Created Successfully!");
            return ResponseEntity.status(HttpStatus.CREATED).body(animal);
        } catch (InvalidAnimalException | ExecutionException e) {
            log.error("Invalid Animal!");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Species Classifier was Interrupted!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Animal!");
    }
}

package com.turisco.learning.controller;

import com.turisco.learning.model.Animal;
import com.turisco.learning.repository.AnimalRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/animais")
public class AnimalController {

    private static final org.slf4j.Logger log
            = org.slf4j.LoggerFactory.getLogger(AnimalController.class);

    private final AnimalRepository repository;

    public AnimalController(AnimalRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listarTodos() {
        List<Animal> animais = repository.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put("total", animais.size());
        response.put("data", animais);
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<Animal> adicionar(@Valid @RequestBody Animal animal) {
        log.info(animal.toString());
        Animal salvo = repository.save(animal);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }
}
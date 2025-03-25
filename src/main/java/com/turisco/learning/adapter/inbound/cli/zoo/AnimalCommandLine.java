package com.turisco.learning.adapter.inbound.cli.zoo;

import com.turisco.learning.adapter.inbound.rest.zoo.dto.AnimalDTO;
import com.turisco.learning.adapter.outbound.persistence.entity.AnimalAttributeInterface;
import com.turisco.learning.adapter.outbound.persistence.enums.AnimalStatus;
import com.turisco.learning.domain.service.AnimalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AnimalCommandLine implements CommandLineRunner {

    private final AnimalService service;

    @Override
    public void run(String... args) {
        log.info("🚀 CLI Spring Boot started!");
        if (args.length == 0) {
            service.findAll()
                    .stream()
                    .map(AnimalAttributeInterface::getName)
                    .forEach(name -> log.info("📛 Animal Name: {}", name));
            return;
        }
        if (args.length < 2) {
            log.error("⚠️ Insufficient arguments! Expected: <name> <age>");
            return;
        }
        try {
            AnimalDTO animalDTO = AnimalDTO.builder()
                    .name(args[0])
                    .age(Integer.parseInt(args[1]))
                    .status(AnimalStatus.INACTIVE)
                    .build();
            service.generateSpeciesName(animalDTO);
            log.info("🐾 Animal Created:");
            log.info("📛 Name: {}", animalDTO.getName());
            log.info("🦴 Species: {}", animalDTO.getSpecies());
            log.info("🎂 Age: {}", animalDTO.getAge());
            log.info("📌 Status: {}", animalDTO.getStatus());
            service.create(animalDTO);
        } catch (NumberFormatException e) {
            log.error("❌ Invalid age format! Age must be a number.");
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            log.error("❌ Error creating AnimalDTO: {}", e.getMessage());
        }
    }
}

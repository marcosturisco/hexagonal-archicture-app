package com.turisco.learning.adapter.inbound.graphql.controller;

import com.turisco.learning.adapter.inbound.rest.dto.AnimalDTO;
import com.turisco.learning.adapter.outbound.persistence.entity.AnimalAttributeInterface;
import com.turisco.learning.adapter.outbound.persistence.enums.AnimalStatus;
import com.turisco.learning.adapter.outbound.persistence.mapper.AnimalEntityMapper;
import com.turisco.learning.domain.exception.InvalidAnimalException;
import com.turisco.learning.domain.service.AnimalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AnimalGraphController {

    private final AnimalService service;
    private final AnimalEntityMapper mapper;

    @QueryMapping
    public List<AnimalDTO> animals() {
        return service.findAll()
                .stream()
                .map(mapper::entityToDto)
                .toList();
    }

    @QueryMapping
    public AnimalDTO animalById(@Argument String id) {
        return mapper.entityToDto(service.get(Long.parseLong(id)));
    }

    @MutationMapping
    public AnimalAttributeInterface createAnimal(@Argument String name,
                                                 @Argument String species,
                                                 @Argument int age) {
        var dto = AnimalDTO
                .builder()
                .name(name)
                .species(species)
                .age(age)
                .status(AnimalStatus.INACTIVE)
                .build();
        try {
            return service.create(dto);
        } catch (InvalidAnimalException e) {
            log.error("Could not create an animal", e);
        }
        return null;
    }
}

package com.turisco.learning.domain.service;

import com.turisco.learning.adapter.inbound.rest.zoo.dto.AnimalDTO;
import com.turisco.learning.adapter.outbound.persistence.entity.AnimalAttributeInterface;
import com.turisco.learning.domain.exception.InvalidAnimalException;
import com.turisco.learning.domain.model.AnimalActionInterface;

import java.util.List;

public interface AnimalServiceInterface {
    AnimalAttributeInterface get(Long id);

    List<AnimalAttributeInterface> findAll();

    AnimalAttributeInterface create(AnimalDTO animalDTO) throws InvalidAnimalException;

    AnimalAttributeInterface activate(AnimalActionInterface animal);

    AnimalAttributeInterface deactivate(AnimalActionInterface animal);
}

package com.turisco.learning.domain.service;

import com.turisco.learning.adapter.inbound.rest.zoo.dto.AnimalDTO;
import com.turisco.learning.adapter.outbound.persistence.entity.AnimalAttributeInterface;
import com.turisco.learning.domain.exception.InvalidAnimalException;
import com.turisco.learning.domain.exception.InvalidSpeciesException;
import com.turisco.learning.domain.model.AnimalActionInterface;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface AnimalServiceInterface {
    AnimalAttributeInterface get(Long id);

    List<AnimalAttributeInterface> findAll();

    AnimalAttributeInterface create(AnimalDTO animalDTO) throws InvalidAnimalException, InvalidSpeciesException, InterruptedException, ExecutionException;

    AnimalAttributeInterface activate(AnimalActionInterface animal);

    AnimalAttributeInterface deactivate(AnimalActionInterface animal);
}

package com.turisco.learning.domain.service;

import com.turisco.learning.adapter.inbound.rest.dto.AnimalDTO;
import com.turisco.learning.adapter.outbound.persistence.entity.AnimalAttributeInterface;
import com.turisco.learning.domain.exception.InvalidAnimalException;
import com.turisco.learning.domain.exception.InvalidSpeciesException;
import com.turisco.learning.domain.model.AnimalActionInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface AnimalServiceInterface {
    AnimalAttributeInterface get(Long id);

    List<AnimalAttributeInterface> findAll();

    Page<AnimalAttributeInterface> findAll(Pageable pageable);

    AnimalAttributeInterface create(AnimalDTO animalDTO) throws InvalidAnimalException, InvalidSpeciesException, InterruptedException, ExecutionException;

    void delete(AnimalDTO animalDTO);

    AnimalAttributeInterface activate(AnimalActionInterface animal);

    AnimalAttributeInterface deactivate(AnimalActionInterface animal);
}

package com.turisco.learning.exception;

import com.turisco.learning.adapter.inbound.rest.dto.AnimalDTO;

public class CreateAnimalException extends RuntimeException {
    public CreateAnimalException(AnimalDTO animalDTO) {
        super(animalDTO.toString() + " could not be created!");
    }
}

package com.turisco.learning.domain.service.factory;

import com.turisco.learning.adapter.inbound.rest.zoo.dto.AnimalDTO;
import com.turisco.learning.adapter.outbound.persistence.entity.AnimalAttributeInterface;

public interface AnimalFactory {
    AnimalAttributeInterface create(AnimalDTO animalDTO);
}

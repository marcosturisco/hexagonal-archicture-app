package com.turisco.learning.domain.service.factory;

import com.turisco.learning.adapter.inbound.rest.zoo.dto.AnimalDTO;
import com.turisco.learning.adapter.outbound.persistence.entity.AnimalAttributeInterface;
import com.turisco.learning.adapter.outbound.persistence.mapper.AnimalEntityMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultAnimalFactory implements AnimalFactory {

    @NonNull
    private final AnimalEntityMapper mapper;

    @Override
    public AnimalAttributeInterface create(AnimalDTO animalDTO) {
        return mapper.dtoToEntity(animalDTO);
    }
}

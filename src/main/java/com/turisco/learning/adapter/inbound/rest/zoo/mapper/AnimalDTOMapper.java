package com.turisco.learning.adapter.inbound.rest.zoo.mapper;

import com.turisco.learning.adapter.inbound.rest.zoo.dto.AnimalDTO;
import com.turisco.learning.adapter.outbound.persistence.enums.AnimalStatus;
import com.turisco.learning.config.MapStructConfig;
import com.turisco.learning.domain.model.Animal;
import com.turisco.learning.domain.model.AnimalActionInterface;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface AnimalDTOMapper {

    Animal dtoToModel(AnimalDTO dto);

    @AfterMapping
    default void afterMapping(@MappingTarget Animal model) {
        if (model.getStatus() == null) {
            model.setStatus(AnimalStatus.INACTIVE);
        }
    }

    com.turisco.learning.adapter.outbound.persistence.entity.Animal actionToEntity(AnimalActionInterface animal);
}

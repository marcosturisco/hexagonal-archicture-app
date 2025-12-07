package com.turisco.learning.adapter.outbound.persistence.mapper;

import com.turisco.learning.adapter.inbound.rest.dto.AnimalDTO;
import com.turisco.learning.adapter.outbound.persistence.entity.Animal;
import com.turisco.learning.adapter.outbound.persistence.entity.AnimalAttributeInterface;
import com.turisco.learning.config.MapStructConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapStructConfig.class)
public interface AnimalEntityMapper {
    com.turisco.learning.domain.model.Animal toAction(AnimalAttributeInterface animal);

    List<com.turisco.learning.domain.model.Animal> modelListToActionList(List<AnimalAttributeInterface> animals);

    List<AnimalAttributeInterface> entityListToAttributeList(List<Animal> animals);

    Animal dtoToEntity(AnimalDTO dto);

    @Mapping(target = "classifiedSpecies", ignore = true)
    AnimalDTO entityToDto(AnimalAttributeInterface dto);
}

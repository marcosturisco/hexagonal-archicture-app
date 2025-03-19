package com.turisco.learning.controller;

import com.turisco.learning.config.MapStructConfig;
import com.turisco.learning.dto.AnimalDTO;
import com.turisco.learning.model.Animal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface AnimalMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "specie", source = "specie")
    Animal toModel(AnimalDTO dto);

}

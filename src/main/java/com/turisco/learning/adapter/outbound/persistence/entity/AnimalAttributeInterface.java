package com.turisco.learning.adapter.outbound.persistence.entity;

import com.turisco.learning.adapter.outbound.persistence.enums.AnimalStatus;

public interface AnimalAttributeInterface {
    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    String getSpecies();

    void setSpecies(String species);

    Integer getAge();

    void setAge(Integer age);

    AnimalStatus getStatus();

    void setStatus(AnimalStatus status);
}

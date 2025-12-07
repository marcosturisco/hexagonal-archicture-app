package com.turisco.learning.adapter.inbound.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turisco.learning.adapter.outbound.persistence.enums.AnimalStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AnimalDTO {
    @JsonIgnore
    private Long id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    private String species;
    private boolean classifiedSpecies;
    @NotNull(message = "Age cannot be empty")
    private Integer age;
    @JsonIgnore
    private AnimalStatus status;

    public AnimalStatus getStatus() {
        return this.classifiedSpecies ? AnimalStatus.ACTIVE : AnimalStatus.INACTIVE;
    }
}

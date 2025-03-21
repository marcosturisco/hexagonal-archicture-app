package com.turisco.learning.adapter.inbound.rest.zoo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turisco.learning.adapter.outbound.persistence.enums.AnimalStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnimalDTO {
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Species cannot be empty")
    private String species;
    @NotNull(message = "Age cannot be empty")
    private Integer age;
    @JsonIgnore
    private AnimalStatus status;
}

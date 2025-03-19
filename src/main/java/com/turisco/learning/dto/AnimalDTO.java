package com.turisco.learning.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnimalDTO {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Specie cannot be empty")
    private String specie;
}

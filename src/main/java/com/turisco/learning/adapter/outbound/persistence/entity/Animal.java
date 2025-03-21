package com.turisco.learning.adapter.outbound.persistence.entity;

import com.turisco.learning.adapter.outbound.persistence.enums.AnimalStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Animal implements AnimalAttributeInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name should be informed")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Species should be informed")
    @Column(nullable = false)
    private String species;

    @NotNull(message = "Age should be informed")
    @Positive(message = "Age must be greater than zero")
    @Column(nullable = false)
    private Integer age;

    @NotNull(message = "Status should be informed")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AnimalStatus status;
}
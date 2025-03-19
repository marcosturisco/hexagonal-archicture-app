package com.turisco.learning.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(of = {"nome", "especie"})
@Entity
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome não pode ser vazio ou nulo")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "Espécie não pode ser vazia ou nula")
    @Column(nullable = false)
    private String especie;
}
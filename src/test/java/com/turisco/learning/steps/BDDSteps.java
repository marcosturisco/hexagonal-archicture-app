package com.turisco.learning.steps;

import com.turisco.learning.adapter.inbound.rest.zoo.dto.AnimalDTO;
import com.turisco.learning.adapter.outbound.persistence.entity.AnimalAttributeInterface;
import com.turisco.learning.domain.exception.InvalidAnimalException;
import com.turisco.learning.domain.service.AnimalService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@RequiredArgsConstructor
public class BDDSteps {

    private final AnimalService service;

    private List<AnimalAttributeInterface> animals;

    @Given("user registered an {string} and {string} animals")
    public void givenUserRegisteredAnimals(String animal1, String animal2) {
        try {
            service.create(AnimalDTO.builder().name(animal1).species("Species 1").age(1).build());
            service.create(AnimalDTO.builder().name(animal2).species("Species 2").age(2).build());
        } catch (InvalidAnimalException e) {
            throw new RuntimeException(e);
        }
    }

    @When("user request the listAllAnimals feature")
    public void whenUserRequestsAnimalList() {
        animals = service.findAll();
    }

    @Then("the system returns the {string} and {string} animals")
    public void theSystemRetursTheAndAnimals(String animal1, String animal2) {
        assertEquals(animals.get(0).getName(), animal1);
        assertEquals(animals.get(1).getName(), animal2);
    }
}

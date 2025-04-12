package com.turisco.learning.steps;

import com.turisco.learning.adapter.inbound.rest.zoo.dto.AnimalDTO;
import com.turisco.learning.adapter.outbound.persistence.entity.AnimalAttributeInterface;
import com.turisco.learning.adapter.outbound.persistence.enums.AnimalStatus;
import com.turisco.learning.domain.exception.InvalidAnimalException;
import com.turisco.learning.domain.service.AnimalService;
import io.cucumber.java.After;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@RequiredArgsConstructor
public class BDDSteps {

    private final AnimalService service;
    private final List<AnimalDTO> animals = new ArrayList<>();
    private List<AnimalAttributeInterface> registeredAnimals = new ArrayList<>();

    @DataTableType
    public AnimalDTO toDTO(Map<String, String> row) {
        return AnimalDTO.builder()
                .name(row.get("name"))
                .species(row.get("species"))
                .age(Integer.parseInt(row.get("age")))
                .status(AnimalStatus.ACTIVE)
                .build();
    }

    @After
    public void cleanUp() {
        if (CollectionUtils.isNotEmpty(animals)) {
            animals.forEach(service::delete);
        }
    }

    @Given("the user is on the animal registration feature")
    public void userIsOnAnimalRegistrationFeature() {
        log.info("User is accessing the animal registration feature.");
    }

    @When("the user submits the following animals for registration")
    public void userSubmitsAnimalsForRegistration(List<AnimalDTO> candidates) {
        if (CollectionUtils.isNotEmpty(candidates)) {
            candidates.forEach(this::registerAnimal);
        }
    }

    @Then("the system should return a confirmation with the details of the registered animals")
    public void systemReturnsRegisteredAnimalDetails(List<AnimalDTO> expected) {
        registeredAnimals = service.findAll();

        assertEquals(expected.size(), registeredAnimals.size(), "Mismatch in number of registered animals");

        for (int i = 0; i < expected.size(); i++) {
            AnimalDTO expectedAnimal = expected.get(i);
            AnimalAttributeInterface actualAnimal = registeredAnimals.get(i);

            assertEquals(expectedAnimal.getName(), actualAnimal.getName());
            assertEquals(expectedAnimal.getSpecies(), actualAnimal.getSpecies());
            assertEquals(expectedAnimal.getAge(), actualAnimal.getAge());
        }
    }

    @Given("the zoo has the following animals registered")
    public void givenZooHasAnimalsRegistered(List<AnimalDTO> candidates) {
        if (CollectionUtils.isNotEmpty(candidates)) {
            candidates.forEach(this::registerAnimal);
        }
    }

    @When("the user requests the list of all animals")
    public void userRequestsListOfAllAnimals() {
        registeredAnimals = service.findAll();
        assertNotNull(registeredAnimals, "Animal list should not be null");
    }

    @Then("the system should display the complete list of registered animals")
    public void systemDisplaysRegisteredAnimals(List<AnimalDTO> expected) {
        List<AnimalAttributeInterface> actual = registeredAnimals;

        assertEquals(expected.size(), actual.size(), "Animal list size mismatch");

        for (int i = 0; i < expected.size(); i++) {
            AnimalDTO expectedAnimal = expected.get(i);
            AnimalAttributeInterface actualAnimal = actual.get(i);

            assertEquals(expectedAnimal.getName(), actualAnimal.getName());
            assertEquals(expectedAnimal.getSpecies(), actualAnimal.getSpecies());
            assertEquals(expectedAnimal.getAge(), actualAnimal.getAge());
        }
    }

    private void registerAnimal(AnimalDTO dto) {
        AnimalAttributeInterface createdAnimal;
        try {
            createdAnimal = service.create(dto);
            dto.setId(createdAnimal.getId());
            animals.add(dto);
        } catch (InvalidAnimalException e) {
            throw new RuntimeException("Failed to register animals", e);
        }
    }
}

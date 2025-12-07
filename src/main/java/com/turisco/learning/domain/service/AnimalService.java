package com.turisco.learning.domain.service;

import com.turisco.learning.adapter.inbound.rest.dto.AnimalDTO;
import com.turisco.learning.adapter.outbound.persistence.entity.AnimalAttributeInterface;
import com.turisco.learning.adapter.outbound.persistence.mapper.AnimalEntityMapper;
import com.turisco.learning.adapter.outbound.persistence.repository.AnimalPersistenceInterface;
import com.turisco.learning.api.classifier.OpenAIUtility;
import com.turisco.learning.domain.exception.InvalidAnimalException;
import com.turisco.learning.domain.model.Animal;
import com.turisco.learning.domain.model.AnimalActionInterface;
import com.turisco.learning.domain.service.factory.AnimalFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnimalService implements AnimalServiceInterface {

    private final AnimalPersistenceInterface persistence;
    private final AnimalFactory animalFactory;
    private final AnimalEntityMapper entityMapper;
    private final OpenAIUtility classifier;

    @Override
    public AnimalAttributeInterface get(Long id) {
        return persistence.get(id);
    }

    @Override
    public List<AnimalAttributeInterface> findAll() {
        return persistence.findAll();
    }

    @Override
    public Page<AnimalAttributeInterface> findAll(Pageable pageable) {
        return persistence.findAll(pageable);
    }

    @Override
    public AnimalAttributeInterface create(AnimalDTO animalDTO) throws InvalidAnimalException {
        AnimalAttributeInterface attribute = animalFactory.create(animalDTO);
        Animal action = entityMapper.toAction(attribute);
        if (!action.isValid()) {
            throw new InvalidAnimalException("Invalid animal data");
        }
        return persistence.save(action);
    }

    @Override
    public void delete(AnimalDTO animalDTO) {
        AnimalAttributeInterface attribute = animalFactory.delete(animalDTO);
        persistence.delete(attribute);
    }

    @Override
    public AnimalAttributeInterface activate(AnimalActionInterface animal) {
        animal.activate();
        return persistence.save(animal);
    }

    @Override
    public AnimalAttributeInterface deactivate(AnimalActionInterface animal) {
        animal.deactivate();
        return persistence.save(animal);
    }

    public void generateSpeciesName(AnimalDTO dto) throws ExecutionException, InterruptedException {
        var species =
                dto.isClassifiedSpecies()
                        ? classifier
                        .openAIResponseString(dto.getName())
                        .thenApply(result -> {
                            log.info("Generated Species Names: {}", result);
                            return result;
                        }).get()
                        : "Species Name Test";
        dto.setSpecies(species);
    }
}

package com.turisco.learning.adapter.outbound.persistence.repository;

import com.turisco.learning.adapter.inbound.rest.mapper.AnimalDTOMapper;
import com.turisco.learning.adapter.outbound.persistence.entity.Animal;
import com.turisco.learning.adapter.outbound.persistence.entity.AnimalAttributeInterface;
import com.turisco.learning.adapter.outbound.persistence.mapper.AnimalEntityMapper;
import com.turisco.learning.domain.model.AnimalActionInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AnimalPersistenceAdapter implements AnimalPersistenceInterface {

    private final AnimalRepository repository;
    private final AnimalDTOMapper dtoMapper;
    private final AnimalEntityMapper entityMapper;

    @Override
    public AnimalAttributeInterface get(Long id) {
        Optional<Animal> animalEntity = repository.findById(id);
        return animalEntity.orElse(null);
    }

    @Override
    public List<AnimalAttributeInterface> findAll() {
        return entityMapper.entityListToAttributeList(repository.findAll());
    }

    @Override
    public Page<AnimalAttributeInterface> findAll(Pageable pageable) {
        var pagedList = repository.findAll(pageable);
        var attList = entityMapper.entityListToAttributeList(pagedList.getContent());
        return new PageImpl<>(attList, pageable, pagedList.getTotalElements());
    }

    @Override
    public AnimalAttributeInterface save(AnimalActionInterface animal) {
        Animal animalEntity = dtoMapper.actionToEntity(animal);
        return repository.save(animalEntity);
    }

    @Override
    public void delete(AnimalAttributeInterface animal) {
        repository.delete((Animal) animal);
    }
}

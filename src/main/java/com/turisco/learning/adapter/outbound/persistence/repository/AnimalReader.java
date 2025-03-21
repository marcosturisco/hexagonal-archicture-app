package com.turisco.learning.adapter.outbound.persistence.repository;

import com.turisco.learning.adapter.outbound.persistence.entity.AnimalAttributeInterface;

import java.util.List;

public interface AnimalReader {
    AnimalAttributeInterface get(Long id);

    List<AnimalAttributeInterface> findAll();
}

package com.turisco.learning.adapter.outbound.persistence.repository;

import com.turisco.learning.adapter.outbound.persistence.entity.AnimalAttributeInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnimalReader {
    AnimalAttributeInterface get(Long id);

    List<AnimalAttributeInterface> findAll();

    Page<AnimalAttributeInterface> findAll(Pageable pageable);
}

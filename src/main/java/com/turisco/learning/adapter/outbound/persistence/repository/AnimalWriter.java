package com.turisco.learning.adapter.outbound.persistence.repository;

import com.turisco.learning.adapter.outbound.persistence.entity.AnimalAttributeInterface;
import com.turisco.learning.domain.model.AnimalActionInterface;

public interface AnimalWriter {
    AnimalAttributeInterface save(AnimalActionInterface animal);

    void delete(AnimalAttributeInterface animal);
}

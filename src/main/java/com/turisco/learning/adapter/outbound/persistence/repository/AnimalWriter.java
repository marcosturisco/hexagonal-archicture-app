package com.turisco.learning.adapter.outbound.persistence.repository;

import com.turisco.learning.domain.model.AnimalActionInterface;
import com.turisco.learning.adapter.outbound.persistence.entity.AnimalAttributeInterface;

public interface AnimalWriter {
    AnimalAttributeInterface save(AnimalActionInterface animal);
}

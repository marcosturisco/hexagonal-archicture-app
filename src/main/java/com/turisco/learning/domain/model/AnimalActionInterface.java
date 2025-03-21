package com.turisco.learning.domain.model;

import com.turisco.learning.adapter.outbound.persistence.entity.AnimalAttributeInterface;
import com.turisco.learning.domain.exception.InvalidAnimalException;

public interface AnimalActionInterface extends AnimalAttributeInterface {
    boolean isValid() throws InvalidAnimalException;

    void activate();

    void deactivate();
}

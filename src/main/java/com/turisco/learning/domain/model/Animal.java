package com.turisco.learning.domain.model;

import com.turisco.learning.adapter.outbound.persistence.enums.AnimalStatus;
import com.turisco.learning.domain.exception.InvalidAnimalException;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Animal implements AnimalActionInterface {
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String species;
    @NonNull
    private Integer age;
    private AnimalStatus status = AnimalStatus.INACTIVE;

    @Override
    public boolean isValid() throws InvalidAnimalException {
        if (status == null) {
            status = AnimalStatus.INACTIVE;
        }
        if (!status.equals(AnimalStatus.ACTIVE) && !status.equals(AnimalStatus.INACTIVE)) {
            throw new InvalidAnimalException("The status must be one of [inactive, active]");
        }
        if (age < 0) {
            throw new InvalidAnimalException("The age must be greater than zero");
        }
        return true;
    }

    @Override
    public void activate() {
        this.status = AnimalStatus.ACTIVE;
    }

    @Override
    public void deactivate() {
        this.status = AnimalStatus.INACTIVE;
    }
}

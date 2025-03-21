package com.turisco.learning.adapter.outbound.persistence.enums;

import lombok.Getter;

@Getter
public enum AnimalStatus {

    INACTIVE("inactive"),
    ACTIVE("active");

    private final String status;

    AnimalStatus(String status) {
        this.status = status;
    }
}

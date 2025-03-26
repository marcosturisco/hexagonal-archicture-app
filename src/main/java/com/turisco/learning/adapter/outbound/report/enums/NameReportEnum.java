package com.turisco.learning.adapter.outbound.report.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NameReportEnum {
    ANIMAL_REPORT("allAnimalsPersisted", "reports/hello_world.jasper");

    private final String nome;
    private final String path;
}

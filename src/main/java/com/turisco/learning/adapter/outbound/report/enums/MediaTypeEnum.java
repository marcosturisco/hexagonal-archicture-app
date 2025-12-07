package com.turisco.learning.adapter.outbound.report.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MediaTypeEnum {

    PDF("pdf", "application/pdf"),
    EXCEL("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    private final String extension;
    private final String mimeType;

}

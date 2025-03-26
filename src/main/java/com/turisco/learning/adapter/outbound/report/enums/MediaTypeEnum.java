package com.turisco.learning.adapter.outbound.report.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MediaTypeEnum {
    PDF("pdf", "application/pdf");

    private final String extension;
    private final String mimeType;
}

package com.turisco.learning.adapter.outbound.report.dto;

import com.turisco.learning.adapter.outbound.report.enums.MediaTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDto {
    private MediaTypeEnum mediaType;
}

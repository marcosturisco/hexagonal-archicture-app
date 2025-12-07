package com.turisco.learning.adapter.outbound.report.main;

import com.turisco.learning.adapter.outbound.report.enums.MediaTypeEnum;

public interface ReportGeneratorStrategy {
    ReportBaseGenerator getGenerator(MediaTypeEnum mediaType);
}

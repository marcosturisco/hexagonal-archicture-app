package com.turisco.learning.adapter.outbound.report.main;

import com.turisco.learning.adapter.outbound.report.enums.MediaTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportStrategy implements ReportGeneratorStrategy {

    private final ReportExcelGenerator reportExcelGenerator;
    private final ReportPdfGenerator reportPdfGenerator;

    @Override
    public ReportBaseGenerator getGenerator(MediaTypeEnum mediaType) {
        return mediaType == MediaTypeEnum.EXCEL ? reportExcelGenerator : reportPdfGenerator;
    }

}

package com.turisco.learning.adapter.outbound.report.conf;

import com.turisco.learning.adapter.outbound.report.enums.MediaTypeEnum;
import com.turisco.learning.adapter.outbound.report.enums.NameReportEnum;
import lombok.Builder;
import lombok.Getter;

@Builder
public class ReportConf {

    @Getter
    private NameReportEnum nameReportEnum;

    private MediaTypeEnum mediaType;

    public boolean isPDF() {
        return MediaTypeEnum.PDF == mediaType;
    }

    public String getContentType() {
        return mediaType.getMimeType();
    }

    public String getNameReport() {
        return nameReportEnum.getNome();
    }

    public String getExtension() {
        return mediaType.getExtension();
    }
}

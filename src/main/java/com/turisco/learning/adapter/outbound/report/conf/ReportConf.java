package com.turisco.learning.adapter.outbound.report.conf;

import com.turisco.learning.adapter.outbound.report.enums.MediaTypeEnum;
import com.turisco.learning.adapter.outbound.report.enums.NameReportEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportConf {

    private NameReportEnum nameReportEnum;
    private MediaTypeEnum mediaType;

    public String getContentType() {
        return mediaType.getMimeType();
    }

    public String getNameReport() {
        return nameReportEnum.getNome();
    }

    public String getExtension() {
        return mediaType.getExtension();
    }

    public String getContentDisposition() {
        return String.format("attachment; filename=%s.%s", getNameReport(), getExtension());
    }

}

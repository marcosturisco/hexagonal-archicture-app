package com.turisco.learning.adapter.outbound.report.main;

import com.turisco.learning.adapter.outbound.report.conf.ReportConf;
import com.turisco.learning.adapter.outbound.report.exception.ReportException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

@Component
public final class ReportExcelGenerator extends ReportBaseGenerator {

    @Override
    public byte[] generate(ReportConf conf, Map<String, Object> parameters, Collection<?> items) throws ReportException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            JRXlsxExporter exporter = new JRXlsxExporter();
            ExporterInput input = new SimpleExporterInput(super.fill(conf, items, parameters));
            OutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(out);
            exporter.setExporterInput(input);
            exporter.setExporterOutput(output);
            exporter.setConfiguration(getDefaultXlsxReportConfiguration());
            exporter.exportReport();
            return out.toByteArray();
        } catch (IOException | JRException e) {
            var errorMessage = "Fail to generate Excel report";
            throw new ReportException(errorMessage, e);
        }
    }

    private SimpleXlsxReportConfiguration getDefaultXlsxReportConfiguration() {
        SimpleXlsxReportConfiguration reportConfigXLS = new SimpleXlsxReportConfiguration();
        reportConfigXLS.setWhitePageBackground(false);
        reportConfigXLS.setOnePagePerSheet(false);
        reportConfigXLS.setDetectCellType(true);
        reportConfigXLS.setRemoveEmptySpaceBetweenRows(true);
        reportConfigXLS.setRemoveEmptySpaceBetweenColumns(true);
        return reportConfigXLS;
    }

}

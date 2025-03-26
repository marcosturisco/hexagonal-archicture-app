package com.turisco.learning.adapter.outbound.report;

import com.turisco.learning.adapter.outbound.report.conf.ReportConf;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {

    private static final String FAIL_TO_BUILD_REPORT = "Fail to build Report";

    private final ReportGenerator reportGenerator;

    public ResponseEntity<byte[]> getResourceReport(ReportConf reportConf,
                                                    Map<String, Object> reportParameters,
                                                    Collection<?> dataSource) {
        return reportConf.isPDF()
                ? getPdfResponse(reportConf, reportParameters, dataSource)
                : null;
    }

    public ResponseEntity<byte[]> getPdfResponse(ReportConf reportConf,
                                                 Map<String, Object> reportParameters,
                                                 Collection<?> dataSource) {
        try {
            var exporter = reportGenerator.toPDF(reportConf, reportParameters, dataSource);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            exportReport(exporter, outputStream);
            byte[] pdfBytes = outputStream.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, getContentDisposition(reportConf));
            headers.add(HttpHeaders.CONTENT_TYPE, reportConf.getContentType());
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(FAIL_TO_BUILD_REPORT.getBytes(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void exportReport(JRPdfExporter exporter, OutputStream os) throws JRException {
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        configuration.setMetadataAuthor("Animal Administration");
        configuration.setCompressed(true);
        exporter.setConfiguration(configuration);
        exporter.exportReport();
    }

    private String getContentDisposition(ReportConf reportConf) {
        return String.format("attachment; filename=%s.%s", reportConf.getNameReport(),
                reportConf.getExtension());
    }
}

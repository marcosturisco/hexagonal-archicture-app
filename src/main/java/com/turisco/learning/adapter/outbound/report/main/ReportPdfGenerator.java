package com.turisco.learning.adapter.outbound.report.main;

import com.turisco.learning.adapter.outbound.report.conf.ReportConf;
import com.turisco.learning.adapter.outbound.report.exception.ReportException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;

@Component
public final class ReportPdfGenerator extends ReportBaseGenerator {

    private static final Logger log = LoggerFactory.getLogger(ReportPdfGenerator.class);

    @Override
    public byte[] generate(ReportConf conf, Map<String, Object> parameters, Collection<?> items) throws ReportException {
        try {
            JRPdfExporter exporter = new JRPdfExporter();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ExporterInput input = new SimpleExporterInput(super.fill(conf, items, parameters));
            exporter.setExporterInput(input);
            exportReport(exporter, outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            var errorMessage = "Fail to generate PDF report";
            log.error(errorMessage, e);
            throw new ReportException(errorMessage, e);
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

}

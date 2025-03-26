package com.turisco.learning.adapter.outbound.report;

import com.turisco.learning.adapter.outbound.report.conf.ReportConf;
import com.turisco.learning.adapter.outbound.report.exception.ReportException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public final class ReportGenerator {

    private static final String FAIL_TO_BUILD_REPORT = "Fail to Build Report";

    private static final Map<String, Object> DEFAULT_PARAMETERS =
            Map.of("header", "ZOO - ADMINISTRATION SYSTEM");

    public JRPdfExporter toPDF(ReportConf conf, Map<String, Object> reportParameters, Collection<?> items) {
        return toPDF(conf, reportParameters, getDataSource(items));
    }

    public JRPdfExporter toPDF(ReportConf conf, Map<String, Object> reportParameters, JRDataSource dataSource) {
        try {
            JasperReport jasperReport = load(conf);
            JasperPrint jasperPrint = fillReport(jasperReport, reportParameters, dataSource);
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            return exporter;
        } catch (Exception e) {
            throw new ReportException(FAIL_TO_BUILD_REPORT, e);
        }
    }

    private JRDataSource getDataSource(Collection<?> items) {
        return CollectionUtils.isNotEmpty(items)
                ? new JRBeanCollectionDataSource(items)
                : new JREmptyDataSource();
    }

    private JasperPrint fillReport(JasperReport jasperReport, Map<String, Object> reportParameters,
                                   JRDataSource dataSource) throws JRException {
        var allParams = getDefaultParameters();
        allParams.putAll(reportParameters);
        return JasperFillManager.fillReport(jasperReport, allParams, dataSource);
    }

    private JasperReport load(ReportConf conf) throws JRException {
        InputStream is =
                Thread.currentThread().getContextClassLoader().getResourceAsStream(conf.getNameReportEnum().getPath());
        if (is == null) {
            throw new JRException("File not found: " + conf.getNameReportEnum().getPath());
        }
        return (JasperReport) JRLoader.loadObject(is);
    }

    private Map<String, Object> getDefaultParameters() {
        return new HashMap<>(DEFAULT_PARAMETERS);
    }
}

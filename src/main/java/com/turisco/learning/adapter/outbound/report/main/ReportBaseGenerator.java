package com.turisco.learning.adapter.outbound.report.main;

import com.turisco.learning.adapter.outbound.report.conf.ReportConf;
import com.turisco.learning.adapter.outbound.report.exception.ReportException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public abstract class ReportBaseGenerator {

    protected static final String ARQUIVO_NAO_ENCONTRADO = "Arquivo não encontrado: ";

    protected static final Map<String, Object> DEFAULT_PARAMETERS =
            Map.of("header", "ZOO - ADMINISTRATION SYSTEM");

    public abstract byte[] generate(
            ReportConf conf, Map<String, Object> parameters, Collection<?> items
    ) throws ReportException;

    protected JasperPrint fill(
            ReportConf conf, Collection<?> items, Map<String, Object> parameters
    ) throws JRException {
        var allParams = getDefaultParameters();
        allParams.putAll(parameters);
        return JasperFillManager.fillReport(load(conf), allParams, getDataSource(items));
    }

    private JasperReport load(ReportConf conf) throws JRException {
        var path = conf.getNameReportEnum().getPath();
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        if (is == null) {
            throw new JRException(ARQUIVO_NAO_ENCONTRADO + conf.getNameReportEnum().getPath());
        }
        return (JasperReport) JRLoader.loadObject(is);
    }

    private JRDataSource getDataSource(Collection<?> items) {
        return CollectionUtils.isNotEmpty(items) ? new JRBeanCollectionDataSource(items) : new JREmptyDataSource();
    }

    private Map<String, Object> getDefaultParameters() {
        return new HashMap<>(DEFAULT_PARAMETERS);
    }

}

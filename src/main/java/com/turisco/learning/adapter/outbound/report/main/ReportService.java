package com.turisco.learning.adapter.outbound.report.main;

import com.turisco.learning.adapter.outbound.report.conf.ReportConf;
import com.turisco.learning.adapter.outbound.report.exception.ReportException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {

    private static final String FAIL_TO_BUILD_REPORT = "Fail to build Report";

    private final ReportGeneratorStrategy reportGeneratorStrategy;

    public byte[] export(ReportConf conf, Map<String, Object> parameters, Collection<?> items) {
        try {
            var mediaType = conf.getMediaType();
            var generator = reportGeneratorStrategy.getGenerator(mediaType);
            return generator.generate(conf, parameters, items);
        } catch (Exception e) {
            throw new ReportException(FAIL_TO_BUILD_REPORT, e);
        }
    }

}

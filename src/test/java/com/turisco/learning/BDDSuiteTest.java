package com.turisco.learning;

import io.cucumber.junit.platform.engine.Cucumber;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Cucumber
@CucumberContextConfiguration
@SpringBootTest
@ActiveProfiles("test")
public final class BDDSuiteTest {
}


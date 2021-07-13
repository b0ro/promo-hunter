package org.boro.promohunter.config;

import org.boro.promohunter.infrastructure.DatabaseCleanup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.contract.wiremock.WireMockConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import java.util.List;

@Configuration
public class IntegrationConfiguration {

    @Value("${databasecleanup.tablenames}")
    private List<String> tableNames;

    @Bean
    public DatabaseCleanup databaseCleanup(EntityManager entityManager) {
        return new DatabaseCleanup(entityManager, tableNames);
    }

    @Bean
    public WireMockConfigurationCustomizer wireMockConfigurationCustomizer() {
        return config -> config.withRootDirectory("src/integration/resources");
    }
}

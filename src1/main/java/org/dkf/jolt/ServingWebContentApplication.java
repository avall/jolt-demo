package org.dkf.jolt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.dkf.jolt.model.JoltExample;
import org.dkf.jolt.model.JoltExamples;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import java.util.Collections;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@SpringBootApplication
public class ServingWebContentApplication extends SpringBootServletInitializer {
    private static Logger LOG = LoggerFactory.getLogger(ServingWebContentApplication.class);

    public static void main(String[] args) {
        LOG.info("ServingWebContentApplication started");
        SpringApplication.run(ServingWebContentApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ServingWebContentApplication.class);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(NON_NULL);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper;
    }

    @Bean
    public ObjectMapper objectMapperSorted() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(NON_NULL);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        return objectMapper;
    }

    @Bean
    @DependsOn("objectMapper")
    List<JoltExample> registeredExamples() {
        try {
            JoltExamples je = objectMapper()
                    .readValue(ServingWebContentApplication.class.getResourceAsStream("/examples.json"), JoltExamples.class);
            LOG.info("examples {}", je);
            return je.examples;
        } catch (Exception e) {
            LOG.error("can not load examples list {}", e.getMessage());
        }

        return Collections.emptyList();
    }
}

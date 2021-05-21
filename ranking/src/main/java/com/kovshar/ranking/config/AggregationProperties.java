package com.kovshar.ranking.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aggregation.application")
@Data
public class AggregationProperties {
    private String rootPath;
    private String indicatorPath;
    private String metadataSearchPath;
}

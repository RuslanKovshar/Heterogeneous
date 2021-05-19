package com.kovshar.ranking;

import com.kovshar.ranking.config.AggregationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AggregationProperties.class)
public class RankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(RankingApplication.class, args);
    }

}

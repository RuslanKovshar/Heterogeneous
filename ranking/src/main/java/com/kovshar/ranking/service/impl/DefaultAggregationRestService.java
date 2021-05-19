package com.kovshar.ranking.service.impl;

import com.kovshar.ranking.config.AggregationProperties;
import com.kovshar.ranking.model.Indicator;
import com.kovshar.ranking.service.AggregationRestService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultAggregationRestService implements AggregationRestService {
    private final RestTemplate restTemplate;
    private final AggregationProperties aggregationProperties;

    @Override
    public List<Indicator> fetchAllIndicators() {
        String url = aggregationProperties.getRootPath() + aggregationProperties.getIndicatorPath();
        return restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<Indicator>>() {
        }).getBody();
    }
}

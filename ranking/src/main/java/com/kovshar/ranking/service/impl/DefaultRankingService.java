package com.kovshar.ranking.service.impl;

import com.kovshar.ranking.model.Indicator;
import com.kovshar.ranking.service.AggregationRestService;
import com.kovshar.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultRankingService implements RankingService {
    private final AggregationRestService restService;

    @Override
    public Object createDefaultSystemRanking() {
        List<Indicator> indicators = restService.fetchAllIndicators();
        indicators.stream().forEach(indicator -> {

        });
        return indicators;
    }
}

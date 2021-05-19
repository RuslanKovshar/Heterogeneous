package com.kovshar.ranking.service.impl;

import com.kovshar.ranking.service.AggregationRestService;
import com.kovshar.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultRankingService implements RankingService {
    private final AggregationRestService restService;

    @Override
    public Object createDefaultSystemRanking() {
        return restService.fetchAllIndicators();
    }
}

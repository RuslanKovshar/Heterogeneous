package com.kovshar.ranking.service;

import com.kovshar.ranking.model.Indicator;

import java.util.List;

public interface AggregationRestService {

    List<Indicator> fetchAllIndicators();
}

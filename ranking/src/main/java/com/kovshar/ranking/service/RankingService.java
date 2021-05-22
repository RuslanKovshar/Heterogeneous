package com.kovshar.ranking.service;

import com.kovshar.ranking.model.IndicatorRating;

import java.util.List;

public interface RankingService {

    List<IndicatorRating> createDefaultSystemRanking();

    Object createUserRanking(String formula);
}

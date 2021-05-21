package com.kovshar.ranking.service;

import com.kovshar.ranking.model.FieldMetadata;
import com.kovshar.ranking.model.Indicator;

import java.util.List;
import java.util.Map;

public interface AggregationRestService {

    List<Indicator> fetchAllIndicators();

    Map<String, FieldMetadata> findMetadataByFieldsIds(List<String> ids);
}

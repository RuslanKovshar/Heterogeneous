package com.kovshar.heterogeneous.repository;

import com.kovshar.heterogeneous.model.Indicator;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IndicatorRepository extends MongoRepository<Indicator, Long> {
    List<Indicator> findAllByIdIn(Long[] ids);
}

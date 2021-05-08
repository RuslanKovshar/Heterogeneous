package com.kovshar.heterogeneous.service;

import com.kovshar.heterogeneous.dto.IndicatorDto;
import com.kovshar.heterogeneous.exceptions.IndicatorNotFoundException;
import com.kovshar.heterogeneous.mapper.IndicatorMapper;
import com.kovshar.heterogeneous.model.DatabaseSequence;
import com.kovshar.heterogeneous.model.Indicator;
import com.kovshar.heterogeneous.repository.IndicatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
@RequiredArgsConstructor
public class IndicatorService {
    private final IndicatorRepository indicatorRepository;
    private final IndicatorMapper mapper;
    private final SequenceService sequenceService;

    public Indicator getById(Long id) {
        return indicatorRepository.findById(id)
                .orElseThrow(() -> new IndicatorNotFoundException(id));
    }

    public List<Indicator> getAllByIds(Long[] ids) {
        return indicatorRepository.findAllByIdIn(ids);
    }

    public List<Indicator> findAll() {
        return indicatorRepository.findAll();
    }

    public Indicator saveIndicator(IndicatorDto indicatorDto) {
        Indicator indicator = mapper.fromIndicatorDto(indicatorDto);
        indicator.setId(sequenceService.generateSequence(Indicator.SEQUENCE_NAME));
        indicator.setUuid(UUID.randomUUID().toString());
        return indicatorRepository.save(indicator);
    }

    public Indicator saveIndicator(Indicator indicator) {
        return indicatorRepository.save(indicator);
    }

    public Indicator deleteById(Long id) {
        Indicator indicator = getById(id);
        indicatorRepository.deleteById(id);
        return indicator;
    }
}

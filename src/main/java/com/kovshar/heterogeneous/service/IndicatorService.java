package com.kovshar.heterogeneous.service;

import com.kovshar.heterogeneous.exceptions.IndicatorNotFoundException;
import com.kovshar.heterogeneous.model.Indicator;
import com.kovshar.heterogeneous.repository.IndicatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IndicatorService {
    private final IndicatorRepository indicatorRepository;

    @Autowired
    public IndicatorService(IndicatorRepository indicatorRepository) {
        this.indicatorRepository = indicatorRepository;
    }

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

    public Indicator saveUser(Indicator indicator) {
        return indicatorRepository.save(indicator);
    }
}

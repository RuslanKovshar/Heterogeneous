package com.kovshar.heterogeneous.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kovshar.heterogeneous.enums.FileExtensions;
import com.kovshar.heterogeneous.model.Indicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndicatorJsonFileService extends AbstractIndicatorFileService {

    @Autowired
    public IndicatorJsonFileService(IndicatorService indicatorService, ObjectMapper objectMapper) {
        super(indicatorService, objectMapper);
    }

    @Override
    public byte[] getContent() {
        List<Indicator> all = indicatorService.findAll();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(all);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public FileExtensions supportedType() {
        return FileExtensions.JSON;
    }
}

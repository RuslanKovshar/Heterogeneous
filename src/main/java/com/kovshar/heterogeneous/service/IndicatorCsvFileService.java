package com.kovshar.heterogeneous.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kovshar.heterogeneous.enums.FileExtensions;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class IndicatorCsvFileService extends AbstractIndicatorFileService {
    private final FieldsService fieldsService;

    @Autowired
    public IndicatorCsvFileService(IndicatorService indicatorService, ObjectMapper objectMapper, FieldsService fieldsService) {
        super(indicatorService, objectMapper);
        this.fieldsService = fieldsService;
    }

    @Override
    public byte[] getContent() {
        TreeSet<Object> value = fieldsService.value();
        List<List<Object>> collect = indicatorService.findAll().stream()
                .map(indicator -> {
                    try {
                        return objectMapper.writeValueAsString(indicator);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException();
                    }
                })
                .map(JSONObject::new)
                .map(obj -> value.stream().map(field -> fieldsService.getDataFromField(obj, field.toString())).collect(Collectors.toList()))
                .collect(Collectors.toList());
        collect.forEach(line -> {
            System.out.println(line);
            System.out.println();
        });
        return indicatorService.findAll().toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public FileExtensions supportedType() {
        return FileExtensions.CSV;
    }
}

package com.kovshar.heterogeneous.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kovshar.heterogeneous.enums.FileExtensions;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.PrintWriter;
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
        TreeSet<String> value = fieldsService.value();
        List<List<Object>> collect = indicatorService.findAll().stream()
                .map(indicator -> {
                    try {
                        return objectMapper.writeValueAsString(indicator);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException();
                    }
                })
                .map(JSONObject::new)
                .map(obj -> value.stream().map(field -> {
                    Object dataFromField = fieldsService.getDataFromField(obj, field);
                    System.out.println(field +"-->"+ dataFromField);
                    return dataFromField;
                }).collect(Collectors.toList()))
                .collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        sb.append(String.join(",",value));
        sb.append("\n");
        collect.forEach(list -> {
            List<String> collect1 = list.stream()
                    .map(val -> val == null ? "" : val.toString())
                    .map(data -> {
                        String escapedData = data.replaceAll("\\R", " ");
                        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
                            data = data.replace("\"", "\"\"");
                            escapedData = "\"" + data + "\"";
                        }
                        return escapedData;
                    })
                    .collect(Collectors.toList());
            sb.append(String.join(",",collect1));
            sb.append("\n");
        });
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public FileExtensions supportedType() {
        return FileExtensions.CSV;
    }
}

package com.kovshar.heterogeneous.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kovshar.heterogeneous.converter.JsonObjectConverter;
import com.kovshar.heterogeneous.enums.FileExtensions;
import com.kovshar.heterogeneous.model.Indicator;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class IndicatorCsvFileService extends AbstractIndicatorFileService {
    private final FieldsService fieldsService;
    private final JsonObjectConverter converter;

    @Autowired
    public IndicatorCsvFileService(IndicatorService indicatorService, ObjectMapper objectMapper,
                                   FieldsService fieldsService, JsonObjectConverter converter) {
        super(indicatorService, objectMapper);
        this.fieldsService = fieldsService;
        this.converter = converter;
    }

    @Override
    @SneakyThrows
    public byte[] getContent(JSONArray jsonArray) {
        List<Object> objects = jsonArray.toList();
        if (objects.isEmpty()) {
            return "".getBytes(StandardCharsets.UTF_8);
        }
        TreeSet<String> names = fieldsService.value(objects);
        List<List<Object>> values = getIndicatorFieldsValues(names, objects);
        return writeCsv(names, values);
    }

    @Override
    public byte[] getContent() {
        TreeSet<String> value = fieldsService.value();
        List<Indicator> indicators = indicatorService.findAll();
        List<List<Object>> collect = getIndicatorFieldsValues(value, Arrays.asList(indicators.toArray()));
        return writeCsv(value, collect);
    }

    private List<List<Object>> getIndicatorFieldsValues(TreeSet<String> value, List<Object> indicators) {
        return indicators.stream()
                .map(converter::createJSONObject)
                .map(obj -> value.stream().map(field -> fieldsService.getDataFromField(obj, field)).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private byte[] writeCsv(Collection<String> value, List<List<Object>> collect) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.join(",", value));
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
            sb.append(String.join(",", collect1));
            sb.append("\n");
        });
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public FileExtensions supportedType() {
        return FileExtensions.CSV;
    }
}

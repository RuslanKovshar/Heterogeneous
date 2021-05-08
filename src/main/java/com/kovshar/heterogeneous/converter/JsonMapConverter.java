package com.kovshar.heterogeneous.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kovshar.heterogeneous.model.Field;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class JsonMapConverter {
    private final ObjectMapper mapper;

    @SneakyThrows
    public JSONObject toJson(Map<String, Object> data){
        return new JSONObject(mapper.writeValueAsString(data));
    }

    @SneakyThrows
    public Map<String, Object> toMap(JSONObject json) {
        return mapper.readValue(json.toString(), new TypeReference<>() {
        });
    }
}

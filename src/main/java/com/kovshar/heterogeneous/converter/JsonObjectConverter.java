package com.kovshar.heterogeneous.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonObjectConverter {
    private final ObjectMapper objectMapper;

    public JSONObject createJSONObject(Object object) {
        try {
            return new JSONObject(objectMapper.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }
}

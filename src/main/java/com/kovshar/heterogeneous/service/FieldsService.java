package com.kovshar.heterogeneous.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kovshar.heterogeneous.model.Indicator;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FieldsService {
    private final IndicatorService indicatorService;
    private final ObjectMapper objectMapper;


    public TreeSet<Object> value() {
        return indicatorService.findAll().stream()
                .map(userToJson())
                .map(jsonToJsonObject())
                .flatMap(obj -> extracted(null, obj).stream())
                .collect(Collectors.toCollection(TreeSet::new));
    }

    private Function<String, JSONObject> jsonToJsonObject() {
        return json -> {
            try {
                return new JSONObject(json);
            } catch (JSONException e) {
                throw new RuntimeException();
            }
        };
    }

    private Function<Indicator, String> userToJson() {
        return user -> {
            try {
                return objectMapper.writeValueAsString(user);
            } catch (JsonProcessingException e) {
                throw new RuntimeException();
            }
        };
    }

    private List<Object> extracted(Object prev, JSONObject obj) {
        Iterator<String> keys = obj.keys();
        List<Object> result = new ArrayList<>();
        while (keys.hasNext()) {
            String current = keys.next();
            Object next;
            if (prev != null) {
                next = prev + "." + current;
            } else {
                next = current;
            }
            result.addAll(List.of(next));
            try {
                JSONObject o = obj.getJSONObject(current);
                List<Object> extracted = extracted(next, o);
                result.addAll(extracted);
            } catch (JSONException ignored) {
            }
        }
        return result;
    }

}

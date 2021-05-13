package com.kovshar.heterogeneous.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kovshar.heterogeneous.model.Indicator;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FieldsService {
    private final IndicatorService indicatorService;
    private final ObjectMapper objectMapper;

    public TreeSet<String> value() {
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

    public Object getDataFromField(JSONObject jsonObject, String fieldName) {
        String[] names = fieldName.split("\\.");
        Object data = null;
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            if (i == names.length - 1) {
                if (jsonObject.has(name)) {
                    data = jsonObject.get(name);
                    if (data instanceof JSONObject) {
                        data = null;
                    }
                }
            } else {
                if (jsonObject.has(name)) {
                    Object value = jsonObject.get(name);
                    if (value instanceof JSONObject) {
                        jsonObject = (JSONObject) value;
                    } else {
                        break;
                    }
                }
            }
        }
        return data;
    }

    private List<String> extracted(String prev, JSONObject obj) {
        Iterator<String> keys = obj.keys();
        List<String> result = new ArrayList<>();
        while (keys.hasNext()) {
            String current = keys.next();
            String next;
            if (prev != null) {
                next = prev + "." + current;
            } else {
                next = current;
            }
            result.addAll(List.of(next));
            try {
                JSONObject o = obj.getJSONObject(current);
                List<String> extracted = extracted(next, o);
                result.addAll(extracted);
            } catch (JSONException ignored) {
            }
        }
        return result;
    }

}

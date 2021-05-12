package com.kovshar.heterogeneous.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kovshar.heterogeneous.model.Indicator;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.util.*;
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

    public Object getDataFromField(JSONObject jsonObject, String fieldName) {
        String[] names = fieldName.split("\\.");
        Object data = null;
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            if (i == names.length - 1) {
                Map<String, Object> stringObjectMap = jsonObject.toMap();
                data = stringObjectMap.get(name);
            } else {
                if (jsonObject.get(name) != null && !jsonObject.get(name).toString().equals("null"))
                    jsonObject = jsonObject.getJSONObject(name);
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

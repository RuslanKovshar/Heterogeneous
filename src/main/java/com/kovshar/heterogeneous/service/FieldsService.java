package com.kovshar.heterogeneous.service;

import com.kovshar.heterogeneous.converter.JsonObjectConverter;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FieldsService {
    private final IndicatorService indicatorService;
    private final JsonObjectConverter converter;

    public TreeSet<String> value() {
        return value(Arrays.asList(indicatorService.findAll().toArray()));
    }

    public TreeSet<String> value(List<Object> indicators) {
        return indicators.stream()
                .map(converter::createJSONObject)
                .flatMap(obj -> getFieldsNamesFromJsonObject(null, obj).stream())
                .collect(Collectors.toCollection(TreeSet::new));
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

    private List<String> getFieldsNamesFromJsonObject(String prev, JSONObject obj) {
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
                List<String> extracted = getFieldsNamesFromJsonObject(next, o);
                result.addAll(extracted);
            } catch (JSONException ignored) {
            }
        }
        return result;
    }

}

package com.kovshar.heterogeneous.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kovshar.heterogeneous.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
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
    private final UserService userService;
    private final ObjectMapper objectMapper;


    public TreeSet<Object> value() {
        return userService.findAll().stream()
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

    private Function<User, String> userToJson() {
        return user -> {
            try {
                return objectMapper.writeValueAsString(user);
            } catch (JsonProcessingException e) {
                throw new RuntimeException();
            }
        };
    }

    private List<Object> extracted(Object next1, JSONObject obj) {
        Iterator keys = obj.keys();
        List<Object> result = new ArrayList<>();
        while (keys.hasNext()) {
            Object next = keys.next();
            Object vpizdu;
            if (next1 != null) {
                vpizdu = next1 + "." + next;
                //System.out.println(next1 + "." + next);
            } else {
                vpizdu = next;
                //System.out.println(next);
            }
            result.addAll(List.of(vpizdu));
            try {
                JSONObject o = obj.getJSONObject(((String) next));
                //result.addAll(List.of(vpizdu));
                List<Object> extracted = extracted(vpizdu, o);
                result.addAll(extracted);
                //    System.out.println(o);
            } catch (JSONException e) {
                //throw new RuntimeException(e);
            }
        }
        return result;
    }

}

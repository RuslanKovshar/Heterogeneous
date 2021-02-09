package com.kovshar.heterogeneous.graphql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kovshar.heterogeneous.model.User;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class FieldGraph implements BaseGraph {


    @GraphQLQuery(name = "fields")
    public Map<String, Object> field(@GraphQLContext User user, @GraphQLArgument(name = "key") List<String> keys) throws JsonProcessingException, JSONException {
        if (keys == null) {
            return user.getFields();
        }
        Map<String, Object> resultMap = new HashMap<>();
        for (String key : keys) {
            if (key.contains(".")) {
                final String[] split = key.split("\\.");
                final String fieldName = split[0];
                final String objectField = split[1];
                final Object o = user.getFields().get(fieldName);
                if (o != null) {
                    try {
                        final Field field1 = o.getClass().getDeclaredField(objectField);
                        field1.setAccessible(true);
                        final Object o1 = field1.get(o);
                        final HashMap<Object, Object> value = new HashMap<>();
                        value.put(objectField, o1);
                        resultMap.put(fieldName, value);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        System.out.println(e.getMessage());
                    }
                }
            } else {
                final Object o = user.getFields().get(key);
                if (o != null) {
                    resultMap.put(key, o);
                }
            }
        }
        return resultMap;
    }

    public static void main(String[] args) {
        final String str = "field1{firstParameter secondParameter}";
        final String rootFieldName = str.substring(0, str.indexOf("{"));
        System.out.println(rootFieldName);
        final String params = str.substring(str.indexOf("{") + 1, str.lastIndexOf("}"));
        System.out.println(params);
    }

}

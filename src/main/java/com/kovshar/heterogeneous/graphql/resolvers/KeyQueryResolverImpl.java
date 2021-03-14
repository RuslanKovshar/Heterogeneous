package com.kovshar.heterogeneous.graphql.resolvers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kovshar.heterogeneous.converter.JsonMapConverter;
import com.kovshar.heterogeneous.graphql.utils.GraphQLFieldsFetcher;
import com.kovshar.heterogeneous.model.Field;
import graphql.language.Selection;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class KeyQueryResolverImpl implements KeyQueryResolver {
    private final GraphQLFieldsFetcher fieldsFetcher;
    private final JsonMapConverter converter;

    @Override
    @SneakyThrows
    public Map<String, Field> resolveQueries(Map<String, Field> data, List<String> queries) {
        List<JSONObject> objects = fetchDataByQueries(data, queries);
        JSONObject jsonObject = mergeResultObjects(objects);
        return converter.toMap(jsonObject);
    }

    private List<JSONObject> fetchDataByQueries(Map<String, Field> data, List<String> queries) {
        return queries.stream()
                .map(query -> fetchDataByQuery(data, query))
                .collect(Collectors.toList());
    }

    private JSONObject fetchDataByQuery(Map<String, Field> data, String query) {
        try {
            JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(data));
            List<graphql.language.Field> list = fieldsFetcher.getFieldsFromQuery(query);
            graphql.language.Field rootField = list.get(0);
            return fetchAndCrateJson(rootField, jsonObject);
        } catch (JSONException | JsonProcessingException e) {
            return new JSONObject();
        }
    }

    private JSONObject fetchAndCrateJson(graphql.language.Field rootField, JSONObject jsonObject) {
        JSONObject resultObject = new JSONObject();
        fetchAndCrateJson(null, rootField, jsonObject, resultObject);
        return resultObject;
    }

    @SneakyThrows
    private void fetchAndCrateJson(graphql.language.Field prev, graphql.language.Field rootField, JSONObject jsonObject, JSONObject resultObject) {
        String fieldName = rootField.getName();
        JSONObject currentResultObject;
        if (prev == null) {
            resultObject.put(fieldName, new JSONObject());
            currentResultObject = resultObject;
        } else {
            currentResultObject = resultObject.getJSONObject(prev.getName());
            currentResultObject.put(fieldName, new JSONObject());
        }

        if (rootField.getSelectionSet() != null) {
            JSONObject object = jsonObject.getJSONObject(fieldName);
            List<Selection> selections = rootField.getSelectionSet().getSelections();
            if (selections != null) {
                selections.forEach(selection -> {
                    if (selection instanceof graphql.language.Field) {
                        graphql.language.Field field = (graphql.language.Field) selection;
                        fetchAndCrateJson(rootField, field, object, currentResultObject);
                    }
                });
            }
        } else {
            Object object = jsonObject.get(fieldName);
            currentResultObject.put(fieldName, object);
        }
    }

    private JSONObject mergeResultObjects(List<JSONObject> objects) {
        JSONObject jsonObject = new JSONObject();
        objects.forEach(obj -> {
            if (obj.keys().hasNext()) {
                String name = ((String) obj.keys().next());
                try {
                    jsonObject.put(name, obj.get(name));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return jsonObject;
    }
}

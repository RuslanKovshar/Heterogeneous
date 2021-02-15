package com.kovshar.heterogeneous.graphql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kovshar.heterogeneous.model.User;
import graphql.language.*;
import graphql.parser.Parser;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.SneakyThrows;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FieldGraph implements BaseGraph {


    @GraphQLQuery(name = "fields")
    public Map<String, Object> field(@GraphQLContext User user, @GraphQLArgument(name = "key") List<String> keys) throws JsonProcessingException, JSONException {
        if (keys == null) {
            Map<String, Object> fields = user.getFields();
            JSONObject jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(fields));
            System.out.println(jsonObject);
            HashMap<String, Object> result =
                    new ObjectMapper().readValue(jsonObject.toString(), HashMap.class);
            return result;
        }

        List<JSONObject> objects = new ArrayList<>();
        keys.forEach(key -> {
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(new ObjectMapper().writeValueAsString(user.getFields()));
                JSONObject resultObject = new JSONObject();
                List<Field> list = parseGraphQLQuery(key);
                Field rootField = list.get(0);
                extracted(null, rootField, jsonObject, resultObject);
                objects.add(resultObject);
            } catch (JSONException | JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        JSONObject jsonObject = new JSONObject();
        objects.forEach(obj -> {
            String name = ((String) obj.keys().next());
            try {
                jsonObject.put(name, obj.get(name));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        return new ObjectMapper().readValue(jsonObject.toString(), HashMap.class);
    }

    private List<Field> parseGraphQLQuery(String query) {
        List<Field> fieldList = new ArrayList<>();

        Parser parser = new Parser();

        Document doc = parser.parseDocument(query);

        List<Definition> definitionList = doc.getDefinitions();
        definitionList.forEach((item) -> {
            if (item instanceof OperationDefinition) {
                OperationDefinition operationDefinition = (OperationDefinition) item;
                SelectionSet selectionSet = operationDefinition.getSelectionSet();
                extractQueryElements(fieldList, selectionSet);
            }
        });

        return fieldList;
    }

    private void extractQueryElements(List<Field> fieldNameList, SelectionSet selectionSet) {
        if (selectionSet != null && selectionSet.getSelections() != null) {
            selectionSet.getSelections().forEach((selection) -> {
                if (selection instanceof Field) {
                    Field field = (Field) selection;
                    fieldNameList.add(field);
                    extractQueryElements(fieldNameList, field.getSelectionSet());
                }
            });
        }
    }

    @SneakyThrows
    private void extracted(Field prev, Field rootField, JSONObject jsonObject, JSONObject resultObject) {
        String fieldName = rootField.getName();
        JSONObject jsonObject1;
        if (prev == null) {
            resultObject.put(fieldName, new JSONObject());
            jsonObject1 = resultObject;
        } else {
            jsonObject1 = resultObject.getJSONObject(prev.getName());
            jsonObject1.put(fieldName, new JSONObject());
        }

        if (rootField.getSelectionSet() != null) {
            JSONObject object = jsonObject.getJSONObject(fieldName);
            List<Selection> selections = rootField.getSelectionSet().getSelections();
            if (selections != null) {
                selections.forEach(selection -> {
                    if (selection instanceof Field) {
                        Field field = (Field) selection;
                        extracted(rootField, field, object, jsonObject1);
                    }
                });
            }
        } else {
            Object object = jsonObject.get(fieldName);
            jsonObject1.put(fieldName, object);
        }
    }
}

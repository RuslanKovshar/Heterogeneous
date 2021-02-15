package com.kovshar.heterogeneous.graphql.utils;

import graphql.language.*;
import graphql.parser.Parser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GraphQLFieldsFetcherImpl implements GraphQLFieldsFetcher {
    @Override
    public List<Field> getFieldsFromQuery(String query) {
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
}

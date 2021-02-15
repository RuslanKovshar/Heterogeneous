package com.kovshar.heterogeneous.graphql.utils;

import graphql.language.Field;

import java.util.List;

public interface GraphQLFieldsFetcher {

    List<Field> getFieldsFromQuery(String query);
}

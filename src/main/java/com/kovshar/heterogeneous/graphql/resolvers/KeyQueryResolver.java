package com.kovshar.heterogeneous.graphql.resolvers;

import com.kovshar.heterogeneous.model.Field;

import java.util.List;
import java.util.Map;

public interface KeyQueryResolver {

    Map<String, Field> resolveQueries(Map<String, Field> data, List<String> queries);
}

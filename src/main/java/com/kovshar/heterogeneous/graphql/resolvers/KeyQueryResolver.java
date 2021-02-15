package com.kovshar.heterogeneous.graphql.resolvers;

import java.util.List;
import java.util.Map;

public interface KeyQueryResolver {

    Map<String, Object> resolveQueries(Map<String, Object> data, List<String> queries);
}

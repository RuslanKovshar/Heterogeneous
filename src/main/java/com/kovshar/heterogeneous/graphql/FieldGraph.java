package com.kovshar.heterogeneous.graphql;

import com.kovshar.heterogeneous.graphql.resolvers.KeyQueryResolver;
import com.kovshar.heterogeneous.model.Field;
import com.kovshar.heterogeneous.model.Indicator;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FieldGraph implements BaseGraph {
    private final KeyQueryResolver keyQueryResolver;

    @GraphQLQuery(name = "fields")
    public Map<String, Field> field(@GraphQLContext Indicator indicator,
                                    @GraphQLArgument(name = "key") List<String> keys) {
        if (keys == null) {
            return indicator.getFields();
        }
        return keyQueryResolver.resolveQueries(indicator.getFields(), keys);
    }
}

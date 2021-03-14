package com.kovshar.heterogeneous.graphql;

import com.kovshar.heterogeneous.graphql.resolvers.KeyQueryResolver;
import com.kovshar.heterogeneous.model.Field;
import com.kovshar.heterogeneous.model.User;
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
    public Map<String, Field> field(@GraphQLContext User user,
                                    @GraphQLArgument(name = "key") List<String> keys) {
        if (keys == null) {
            return user.getFields();
        }
        return keyQueryResolver.resolveQueries(user.getFields(), keys);
    }
}

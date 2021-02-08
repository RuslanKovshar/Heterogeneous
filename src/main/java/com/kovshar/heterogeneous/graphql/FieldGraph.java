package com.kovshar.heterogeneous.graphql;

import com.kovshar.heterogeneous.model.User;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FieldGraph implements BaseGraph {


    @GraphQLQuery(name = "fields")
    public Map<String, Object> field(@GraphQLContext User user, @GraphQLArgument(name = "key") String key) {
        if (key == null) {
            return user.getFields();
        }
        List<String> keys = Arrays.stream(key.split(",")).collect(Collectors.toList());
        return user.getFields().entrySet().stream()
                .filter(entry -> keys.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}

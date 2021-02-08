package com.kovshar.heterogeneous.graphql;

import com.kovshar.heterogeneous.model.Pair;
import com.kovshar.heterogeneous.model.User;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DataGraph implements BaseGraph{


    @GraphQLQuery(name = "data")
    public Pair pair(@GraphQLContext User user, @GraphQLArgument(name = "key") String key) {
        return user.getData().stream().filter(pairKey -> pairKey.getKey().equals(key)).collect(Collectors.toList()).stream().findFirst().orElse(new Pair());
    }

}

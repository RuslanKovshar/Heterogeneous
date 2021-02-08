package com.kovshar.heterogeneous.graphql;

import com.kovshar.heterogeneous.model.Pair;
import com.kovshar.heterogeneous.model.User;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatasGraph implements BaseGraph {

    @GraphQLQuery(name = "datas")
    public List<Pair> pair(@GraphQLContext User user) {
        return user.getData();
    }
}

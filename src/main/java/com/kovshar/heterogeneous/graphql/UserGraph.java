package com.kovshar.heterogeneous.graphql;

import com.kovshar.heterogeneous.model.User;
import com.kovshar.heterogeneous.service.UserService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserGraph implements BaseGraph {
    private final UserService userService;

    @Autowired
    public UserGraph(UserService userService) {
        this.userService = userService;
    }

    @GraphQLQuery(name = "user")
    public List<User> user(@GraphQLArgument(name = "id") Long[] ids) {
        if (ids == null) {
            return userService.findAll();
        }
        return userService.getAllByIds(ids);
    }
}

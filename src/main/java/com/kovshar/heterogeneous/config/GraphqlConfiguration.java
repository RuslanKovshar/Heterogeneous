package com.kovshar.heterogeneous.config;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.kovshar.heterogeneous.model.User;
import com.kovshar.heterogeneous.service.UserService;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphqlConfiguration {
    //private final UserService userService;
/*
    @Autowired
    public GraphqlConfiguration(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public GraphQLSchema graphQLSchema() {
        return new GraphQLSchemaGenerator()
                .withBasePackages("com.kovshar.heterogeneous")
                .withOperationsFromSingleton(userService)
                .generate();
    }

    @Bean
    public GraphQL graphQL() {
        return new GraphQL.Builder(graphQLSchema())
                .build();
    }*/
}

package com.kovshar.heterogeneous.config;

import com.kovshar.heterogeneous.graphql.BaseGraph;
import graphql.GraphQL;
import graphql.analysis.MaxQueryComplexityInstrumentation;
import graphql.analysis.MaxQueryDepthInstrumentation;
import graphql.execution.batched.BatchedExecutionStrategy;
import graphql.execution.instrumentation.ChainedInstrumentation;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
import io.leangen.graphql.metadata.strategy.query.BeanResolverBuilder;
import io.leangen.graphql.metadata.strategy.query.PublicResolverBuilder;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class GraphqlConfiguration {
    private final List<BaseGraph> graphList;

    @Bean
    public GraphQLSchemaGenerator graphQLSchemaGenerator() {
        GraphQLSchemaGenerator graphQLSchemaGenerator = new GraphQLSchemaGenerator()
                .withResolverBuilders(
                        new BeanResolverBuilder("com.kovshar.heterogeneous"),
                        new AnnotatedResolverBuilder(),
                        new PublicResolverBuilder("com.kovshar.heterogeneous"));

        for (BaseGraph baseGraph : graphList) {
            graphQLSchemaGenerator = graphQLSchemaGenerator.withOperationsFromSingleton(baseGraph);
        }
        return graphQLSchemaGenerator;
    }

    @Bean
    public GraphQLSchema schema() {
        return graphQLSchemaGenerator()
                .withValueMapperFactory(new JacksonValueMapperFactory())
                .generate();
    }

    @Bean
    public GraphQL graphQL() {
        return GraphQL.newGraphQL(schema())
                .queryExecutionStrategy(new BatchedExecutionStrategy())
                .instrumentation(new ChainedInstrumentation(Arrays.asList(
                        new MaxQueryComplexityInstrumentation(200),
                        new MaxQueryDepthInstrumentation(20)
                )))
                .build();
    }
}

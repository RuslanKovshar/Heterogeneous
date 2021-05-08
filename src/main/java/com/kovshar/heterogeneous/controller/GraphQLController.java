package com.kovshar.heterogeneous.controller;

import com.kovshar.heterogeneous.service.FieldsService;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.TreeSet;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GraphQLController {
    private final GraphQL graphQL;
    private final FieldsService fieldsService;

    @PostMapping(value = "/graphql", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ExecutionResult execute(@RequestBody Map<String, Object> request) {
        ExecutionResult execute = graphQL.execute(ExecutionInput.newExecutionInput()
                .query((String) request.get("query"))
                .operationName((String) request.get("operationName"))
                .build());
        log.debug("Result: {}", execute);
        return execute;
    }

    @GetMapping("/fields")
    public TreeSet<Object> value() {
        return fieldsService.value();
    }
}

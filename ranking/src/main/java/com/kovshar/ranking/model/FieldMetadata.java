package com.kovshar.ranking.model;

import lombok.Data;

import java.util.List;

@Data
public class FieldMetadata {
    private String fieldId;
    private String type;
    private LogicOperation operation;
    private List<Filter> filters;
}

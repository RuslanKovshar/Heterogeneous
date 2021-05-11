package com.kovshar.heterogeneous.dto;

import com.kovshar.heterogeneous.model.Filter;
import com.kovshar.heterogeneous.model.LogicOperation;
import lombok.Data;

import java.util.List;

@Data
public class FieldMetadataDto {
    private String fieldId;
    private String type;
    private LogicOperation operation;
    private List<Filter> filters;
}

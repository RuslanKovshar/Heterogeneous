package com.kovshar.heterogeneous.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "fields_metadata")
public class FieldMetadata {
    private String fieldId;
    private String type;
    private LogicOperation operation;
    private List<Filter> filters;

    public FieldMetadata(String fieldId, String type, List<Filter> filters) {
        this(fieldId, type, LogicOperation.AND, filters);
    }
}

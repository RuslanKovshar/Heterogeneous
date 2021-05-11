package com.kovshar.heterogeneous.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document(collection = "fields_metadata")
public class FieldMetadata {
    @Transient
    public static final String SEQUENCE_NAME = "fields_metadata_sequence";

    @Id
    private Long id;
    private String fieldId;
    private String type;
    private LogicOperation operation;
    private List<Filter> filters;

    public FieldMetadata(String fieldId, String type, LogicOperation operation, List<Filter> filters) {
        this.fieldId = fieldId;
        this.type = type;
        this.operation = operation;
        this.filters = filters;
    }

    public FieldMetadata(String fieldId, String type, List<Filter> filters) {
        this(fieldId, type, LogicOperation.AND, filters);
    }
}

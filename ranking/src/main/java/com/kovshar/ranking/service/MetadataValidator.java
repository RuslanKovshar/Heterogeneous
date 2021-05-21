package com.kovshar.ranking.service;

import com.kovshar.ranking.model.ComparisionOperator;
import com.kovshar.ranking.model.FieldMetadata;
import com.kovshar.ranking.model.Filter;
import com.kovshar.ranking.model.LogicOperation;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class MetadataValidator {

    public void validateFieldByMetadata(String fieldId, String value, FieldMetadata metadata) {
        String type = metadata.getType();

        LogicOperation operation = metadata.getOperation();
        boolean filterMatch;
        if (operation == LogicOperation.AND) {
            filterMatch = metadata.getFilters().stream().allMatch(validateFieldValue(fieldId, value, type));
        } else {
            filterMatch = metadata.getFilters().stream().anyMatch(validateFieldValue(fieldId, value, type));
        }
        if (!filterMatch) {
            throw new RuntimeException("There is a invalid values at indicators fields");
        }
    }

    private Predicate<Filter> validateFieldValue(String fieldId, String value, String type) {
        return filter -> {
            ComparisionOperator operator = filter.getOperator();
            String filterValue = filter.getValue();
            if ("INT".equalsIgnoreCase(type)) {
                try {
                    int fieldValue = Integer.parseInt(value);
                    int intFilterValue = Integer.parseInt(filterValue);

                    switch (operator) {
                        case EQUAL:
                            return fieldValue == intFilterValue;
                        case NOT_EQUAL:
                            return fieldValue != intFilterValue;
                        case LESS:
                            return fieldValue < intFilterValue;
                        case LESS_EQUAL:
                            return fieldValue <= intFilterValue;
                        case BIGGER:
                            return fieldValue > intFilterValue;
                        case BIGGER_EQUAL:
                            return fieldValue >= intFilterValue;
                        default:
                            return false;
                    }
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Field " + fieldId + " is not " + type);
                }
            } else {
                throw new RuntimeException("Type " + type + " not supported yet");
            }
        };
    }
}

package com.kovshar.heterogeneous.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Filter {
    private ComparisionOperator operator;
    private String value;
}

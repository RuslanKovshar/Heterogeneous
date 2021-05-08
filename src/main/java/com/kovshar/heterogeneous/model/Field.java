package com.kovshar.heterogeneous.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Field {
    private String uuid;
    private Object data;
}

package com.kovshar.heterogeneous.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

    private String name;
    private Long id;
    private Date registrationDate;
    //private List<Pair> data;
    private Map<String, Object> fields;
}

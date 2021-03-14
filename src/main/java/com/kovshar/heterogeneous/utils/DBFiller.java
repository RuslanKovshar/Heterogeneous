package com.kovshar.heterogeneous.utils;

import com.kovshar.heterogeneous.model.Field;
import com.kovshar.heterogeneous.model.Filter;
import com.kovshar.heterogeneous.model.LogicOperation;
import com.kovshar.heterogeneous.model.User;
import com.kovshar.heterogeneous.repository.UserRepository;
import com.kovshar.heterogeneous.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

import static com.kovshar.heterogeneous.model.ComparisionOperator.*;
import static java.util.List.of;

@Component
@RequiredArgsConstructor
@Slf4j
public class DBFiller {
    private final UserService userService;
    private final UserRepository userRepository;

    @PostConstruct
    public void initData() {
        userRepository.deleteAll();
        if (userService.findAll().isEmpty()) {
            for (int i = 0; i < 10; i++) {
                User user = new User();
                user.setId((long) i);
                user.setName("User" + i);
                user.setRegistrationDate(new Date());
                Map<String, Field> fields = new HashMap<>();
                for (int j = 0; j < i + 1; j++) {
                    String uuid = UUID.randomUUID().toString();
                    final String value = UUID.randomUUID().toString();
                    Field string = new Field("String", Collections.emptyList(), value);
                    if (j == 0 && i == 0) {
                        HashMap<String, Field> data = new HashMap<>();
                        data.put("percentage", new Field(
                                "Int",
                                of(new Filter(LESS_EQUAL, "100"), new Filter(BIGGER_EQUAL, "0")),
                                90));
                        data.put("name", new Field(
                                "String",
                                LogicOperation.OR,
                                of(new Filter(EQUAL, "Ruslan"), new Filter(EQUAL, "Anatolii")),
                                "Ruslan"));
                        data.put("count", new Field("Int", Collections.emptyList(), 9));
                        Field field = new Field("Object", Collections.emptyList(), data);
                        fields.put("field" + j, field);
                    } else {
                        fields.put("field" + j, string);
                    }
                }
                user.setFields(fields);
                userService.saveUser(user);
            }
        }
    }
}

package com.kovshar.heterogeneous.utils;

import com.kovshar.heterogeneous.model.DataClass;
import com.kovshar.heterogeneous.model.User;
import com.kovshar.heterogeneous.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class DBFiller {
    private final UserService userService;

    @PostConstruct
    public void initData() {
        if (userService.findAll().isEmpty()) {
            for (int i = 0; i < 10; i++) {
                User user = new User();
                user.setId((long) i);
                user.setName("User" + i);
                user.setRegistrationDate(new Date());
                Map<String, Object> fields = new HashMap<>();
                for (int j = 0; j < i + 1; j++) {
                    final Random random = new Random();
                    final DataClass value = new DataClass(UUID.randomUUID().toString(),
                            random.nextInt(),
                            random.nextLong());
                    fields.put("field" + j, value);
                }
                user.setFields(fields);
                userService.saveUser(user);
            }
        }
    }
}

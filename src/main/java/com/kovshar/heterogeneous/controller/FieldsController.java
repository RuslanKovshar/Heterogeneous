package com.kovshar.heterogeneous.controller;

import com.kovshar.heterogeneous.service.FieldsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.TreeSet;

@RestController
@RequestMapping("/fields")
@Slf4j
@RequiredArgsConstructor
public class FieldsController {
    private final FieldsService fieldsService;

    @GetMapping
    public TreeSet<Object> value() {
        log.debug("Find all fields");
        TreeSet<Object> value = fieldsService.value();
        log.debug("Found fields {}", value);
        return value;
    }
}

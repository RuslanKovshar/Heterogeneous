package com.kovshar.ranking.service;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class FieldsIdsFetcher {
    private static final  Pattern PATTERN = Pattern.compile("[a-zA-Z_$][a-zA-Z_$0-9]*\\.?([a-zA-Z_$][a-zA-Z_$0-9]*\\.?)");

    public List<String> fetchFieldsIds(String formula) {
        Matcher matcher = PATTERN.matcher(formula);
        List<String> ids = new ArrayList<>();
        while (matcher.find()) {
            String group = matcher.group();
            ids.add(group);
        }
        return ids;
    }
}

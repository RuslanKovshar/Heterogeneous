package com.kovshar.heterogeneous.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public abstract class AbstractIndicatorFileService implements FileService {
    protected static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss");
    protected final IndicatorService indicatorService;
    protected final ObjectMapper objectMapper;

    @Override
    public String getFileName(String title) {
        return LocalDateTime.now().format(DATE_TIME_FORMATTER) + "_" + title + supportedType().getExtension();
    }

}

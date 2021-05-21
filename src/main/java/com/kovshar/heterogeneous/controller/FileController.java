package com.kovshar.heterogeneous.controller;

import com.kovshar.heterogeneous.enums.FileExtensions;
import com.kovshar.heterogeneous.providers.IndicatorFileServicesProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@Slf4j
public class FileController {
    private final IndicatorFileServicesProvider indicatorFileServicesProvider;

    @GetMapping(path = "/indicator/download/{type}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadIndicatorFile(@PathVariable FileExtensions type) {
        return indicatorFileServicesProvider.getResource(type, "indicator");
    }

    @PostMapping(path = "/indicator/download/by-query/{type}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadIndicatorFileByQuery(@PathVariable FileExtensions type,
                                                                 @RequestBody String query) {
        return indicatorFileServicesProvider.getResourceByQuery(type, "indicator", query);
    }
}

package com.kovshar.heterogeneous.providers;

import com.kovshar.heterogeneous.enums.FileExtensions;
import com.kovshar.heterogeneous.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class IndicatorFileServicesProvider {
    private Map<FileExtensions, FileService> serviceMap;

    @Autowired
    public void setUp(List<FileService> services) {
        serviceMap = services.stream()
                .collect(Collectors.toMap(FileService::supportedType, Function.identity()));
    }

    public ResponseEntity<Resource> getResource(FileExtensions fileExtension, String title) {
        FileService fileService = serviceMap.get(fileExtension);
        byte[] content = fileService.getContent();
        String fileName = fileService.getFileName(title);
        ByteArrayResource byteArrayResource = new ByteArrayResource(content);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(byteArrayResource);
    }
}

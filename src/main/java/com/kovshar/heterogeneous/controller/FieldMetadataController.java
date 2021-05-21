package com.kovshar.heterogeneous.controller;

import com.kovshar.heterogeneous.dto.FieldMetadataDto;
import com.kovshar.heterogeneous.model.FieldMetadata;
import com.kovshar.heterogeneous.service.FieldMetadataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(("/field-metadata"))
@RequiredArgsConstructor
@Slf4j
public class FieldMetadataController {
    private final FieldMetadataService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FieldMetadata createFieldMetadata(@RequestBody FieldMetadataDto fieldMetadataDto) {
        log.debug("Create FieldMetadata {}", fieldMetadataDto);
        FieldMetadata fieldMetadata = service.save(fieldMetadataDto);
        log.debug("Created FieldMetadata {}", fieldMetadata);
        return fieldMetadata;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FieldMetadata updateFieldMetadata(@RequestBody FieldMetadata fieldMetadata) {
        log.debug("Update FieldMetadata {}", fieldMetadata);
        FieldMetadata saveFieldMetadata = service.save(fieldMetadata);
        log.debug("Updated FieldMetadata {}", saveFieldMetadata);
        return saveFieldMetadata;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FieldMetadata> getFieldMetadatas() {
        log.debug("Get all FieldMetadatas");
        List<FieldMetadata> all = service.findAll();
        log.debug("Get FieldMetadatas {}", all);
        return all;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FieldMetadata getFieldMetadata(@PathVariable Long id) {
        log.debug("Get FieldMetadata by id {}", id);
        FieldMetadata fieldMetadata = service.findById(id);
        log.debug("Got FieldMetadata {}", fieldMetadata);
        return fieldMetadata;
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FieldMetadata deleteFieldMetadata(@PathVariable Long id) {
        log.debug("Delete FieldMetadata by id {}", id);
        FieldMetadata fieldMetadata = service.delete(id);
        log.debug("Deleted FieldMetadata {}", fieldMetadata);
        return fieldMetadata;
    }

    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, FieldMetadata> searchMetadataByFieldsId(@RequestBody List<String> ids) {
        log.debug("Search FieldMetadata by fieldsIds {} ", ids);
        Map<String, FieldMetadata> metadata = service.getAllMetadataByFieldsIds(ids);
        log.debug("Found FieldMetadata by fieldsIds {} ", ids);
        return metadata;
    }
}

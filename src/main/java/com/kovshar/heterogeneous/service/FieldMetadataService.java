package com.kovshar.heterogeneous.service;

import com.kovshar.heterogeneous.dto.FieldMetadataDto;
import com.kovshar.heterogeneous.mapper.FieldMetadataMapper;
import com.kovshar.heterogeneous.model.FieldMetadata;
import com.kovshar.heterogeneous.model.Indicator;
import com.kovshar.heterogeneous.repository.FieldsMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FieldMetadataService {
    private final FieldsMetadataRepository repository;
    private final FieldMetadataMapper mapper;
    private final SequenceService sequenceService;

    public List<FieldMetadata> findAll() {
        return repository.findAll();
    }

    public FieldMetadata findById(Long id) {
        return repository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    public FieldMetadata save(FieldMetadataDto fieldMetadataDto) {
        FieldMetadata fieldMetadata = mapper.fromDto(fieldMetadataDto);
        fieldMetadata.setId(sequenceService.generateSequence(FieldMetadata.SEQUENCE_NAME));
        return repository.save(fieldMetadata);
    }

    public FieldMetadata save(FieldMetadata fieldMetadata) {
        return repository.save(fieldMetadata);
    }

    public FieldMetadata delete(Long id) {
        final FieldMetadata metadata = findById(id);
        repository.delete(metadata);
        return metadata;
    }

}

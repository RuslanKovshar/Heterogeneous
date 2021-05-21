package com.kovshar.heterogeneous.repository;

import com.kovshar.heterogeneous.model.FieldMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FieldsMetadataRepository extends MongoRepository<FieldMetadata, Long> {

    List<FieldMetadata> findAllByFieldIdIn(List<String> fieldsId);
}

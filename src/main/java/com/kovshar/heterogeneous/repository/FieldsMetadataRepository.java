package com.kovshar.heterogeneous.repository;

import com.kovshar.heterogeneous.model.FieldMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FieldsMetadataRepository extends MongoRepository<FieldMetadata, Long> {
}

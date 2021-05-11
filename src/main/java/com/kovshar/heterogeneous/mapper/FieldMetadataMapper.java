package com.kovshar.heterogeneous.mapper;

import com.kovshar.heterogeneous.dto.FieldMetadataDto;
import com.kovshar.heterogeneous.model.FieldMetadata;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FieldMetadataMapper {

    FieldMetadata fromDto(FieldMetadataDto fieldMetadataDto);
}

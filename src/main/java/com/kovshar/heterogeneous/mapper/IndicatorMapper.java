package com.kovshar.heterogeneous.mapper;

import com.kovshar.heterogeneous.dto.IndicatorDto;
import com.kovshar.heterogeneous.model.Indicator;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IndicatorMapper {

    Indicator fromIndicatorDto(IndicatorDto indicatorDto);
}

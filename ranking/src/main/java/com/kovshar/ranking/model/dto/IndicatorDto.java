package com.kovshar.ranking.model.dto;

import com.kovshar.ranking.model.Indicator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndicatorDto {
    private String uuid;
    private String organization;

    public static IndicatorDto of(Indicator indicator) {
        return new IndicatorDto(indicator.getUuid(), indicator.getOrganization());
    }
}

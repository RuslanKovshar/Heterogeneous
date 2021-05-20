package com.kovshar.ranking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kovshar.ranking.model.dto.IndicatorDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndicatorRating {
    private int place;
    private BigDecimal weightedAmount;
    @JsonProperty("indicator")
    private IndicatorDto indicatorDto;
}

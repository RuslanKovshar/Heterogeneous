package com.kovshar.ranking.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WightedIndicator {
    private double wightedAmount;
    private Indicator indicator;
}

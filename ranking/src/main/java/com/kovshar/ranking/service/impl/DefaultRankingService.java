package com.kovshar.ranking.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kovshar.ranking.model.*;
import com.kovshar.ranking.model.dto.IndicatorDto;
import com.kovshar.ranking.service.AggregationRestService;
import com.kovshar.ranking.service.MetadataValidator;
import com.kovshar.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.kovshar.ranking.utils.FieldsUtils.getDataFromField;

@Service
@RequiredArgsConstructor
public class DefaultRankingService implements RankingService {
    private final AggregationRestService restService;
    private final ObjectMapper mapper;
    private final MetadataValidator validator;

    @Override
    public List<IndicatorRating> createDefaultSystemRanking() {
        List<Indicator> indicators = restService.fetchAllIndicators();
        List<WightedIndicator> wightedIndicators = getWightedIndicators(indicators);
        return createWightedIndicatorRating(wightedIndicators);
    }

    private List<IndicatorRating> createWightedIndicatorRating(List<WightedIndicator> wightedIndicators) {
        List<IndicatorRating> indicatorRatingList = new ArrayList<>(wightedIndicators.size());
        for (int i = 0; i < wightedIndicators.size(); i++) {
            WightedIndicator wightedIndicator = wightedIndicators.get(i);
            BigDecimal weightedAmount = BigDecimal.valueOf(wightedIndicator.getWightedAmount()).setScale(2, RoundingMode.HALF_UP);
            IndicatorDto indicatorDto = IndicatorDto.of(wightedIndicator.getIndicator());
            IndicatorRating indicatorRating = new IndicatorRating(i + 1, weightedAmount, indicatorDto);
            indicatorRatingList.add(indicatorRating);
        }
        return indicatorRatingList;
    }

    private List<WightedIndicator> getWightedIndicators(List<Indicator> indicators) {
        return indicators.stream()
                .map(toWightedIndicator())
                .sorted(Comparator.comparingDouble(WightedIndicator::getWightedAmount).reversed())
                .collect(Collectors.toList());
    }

    private Function<Indicator, WightedIndicator> toWightedIndicator() {
        return i -> {
            double wightedAmount = calculateWightedAmount(i);
            return new WightedIndicator(wightedAmount, i);
        };
    }

    @SneakyThrows
    private double calculateWightedAmount(Indicator i) {
        validateIndicatorByDefaultRatingFields(i);

        double pms = 0.8 * i.getShareOfPublicationsWithForeignAuthorsPercentage();
        double pdi = 0.2 * i.getForeignResearchersNumber() * i.getDaysUsedByForeignResearchersNumber() / i.getManDaysPerYear();
        double internationalScientificCooperation = pms + pdi;

        double pzn = 0.5 * i.getShareScientistsInvolved();
        double pmm = 0.5 * i.getShareScientistsSecondedPercentage();
        double internationalResearchEnvironmentIntegration = pzn + pmm;

        double pzf = 0.35 * i.getForeignFinancingPercentage();
        double pef = 0.3 * i.getFundingEfficiency();
        double psp = 0.35 * i.getOrganizationFundingSharePercentage();
        double financialComponent = pzf + pef + psp;

        double pmz = 0.5 * i.getShareInternationalPersonnelSelectionPercentage();
        double pmo = 0.5 * i.getShareOfForeignExpert();
        double internationalOrientationOfAdministration = pmz + pmo;

        return 0.25 * internationalScientificCooperation +
                0.25 * internationalResearchEnvironmentIntegration +
                0.25 * financialComponent +
                0.25 * internationalOrientationOfAdministration;
    }

    private void validateIndicatorByDefaultRatingFields(Indicator i) throws JsonProcessingException {
        List<String> fieldsIds = List.of(
                "shareOfPublicationsWithForeignAuthorsPercentage",
                "foreignResearchersNumber",
                "daysUsedByForeignResearchersNumber",
                "manDaysPerYear",
                "shareScientistsInvolved",
                "shareScientistsSecondedPercentage",
                "foreignFinancingPercentage",
                "fundingEfficiency",
                "organizationFundingSharePercentage",
                "shareInternationalPersonnelSelectionPercentage",
                "shareOfForeignExpert");
        Map<String, FieldMetadata> fieldMetadataMap = restService.findMetadataByFieldsIds(fieldsIds);

        String json = mapper.writeValueAsString(i);
        fieldsIds.forEach(fieldId -> {
            String value = getDataFromField(new JSONObject(json), fieldId).toString();
            FieldMetadata metadata = fieldMetadataMap.get(fieldId);
            validator.validateFieldByMetadata(fieldId, value, metadata);
        });
    }
}

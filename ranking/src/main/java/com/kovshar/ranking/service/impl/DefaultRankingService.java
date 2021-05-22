package com.kovshar.ranking.service.impl;

import com.kovshar.ranking.conventer.JsonObjectConverter;
import com.kovshar.ranking.model.FieldMetadata;
import com.kovshar.ranking.model.Indicator;
import com.kovshar.ranking.model.IndicatorRating;
import com.kovshar.ranking.model.WightedIndicator;
import com.kovshar.ranking.model.dto.IndicatorDto;
import com.kovshar.ranking.service.AggregationRestService;
import com.kovshar.ranking.service.FieldsIdsFetcher;
import com.kovshar.ranking.service.MetadataValidator;
import com.kovshar.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.kovshar.ranking.utils.FieldsUtils.getDataFromField;

@Service
@RequiredArgsConstructor
public class DefaultRankingService implements RankingService {
    private final AggregationRestService restService;
    private final MetadataValidator validator;
    private final JsonObjectConverter converter;
    private final FieldsIdsFetcher fieldsIdsFetcher;

    @Override
    public List<IndicatorRating> createDefaultSystemRanking() {
        List<Indicator> indicators = restService.fetchAllIndicators();
        List<WightedIndicator> wightedIndicators = getWightedIndicators(indicators, this.toWightedIndicator());
        return createWightedIndicatorRating(wightedIndicators);
    }

    @Override
    public Object createUserRanking(String formula) {
        List<Indicator> indicators = restService.fetchAllIndicators();
        List<String> ids = fieldsIdsFetcher.fetchFieldsIds(formula);
        List<WightedIndicator> wightedIndicators = getWightedIndicators(indicators, this.toFormulaWightedIndicator(formula, ids));
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

    private Function<Indicator, WightedIndicator> toFormulaWightedIndicator(String formula, List<String> ids) {
        return i -> {
            validateIndicatorByDefaultRatingFields(i, ids);
            JSONObject jsonObject = converter.createJSONObject(i);
            Map<String, Double> variables = new HashMap<>();
            ids.forEach(fieldId -> {
                String value = getDataFromField(jsonObject, fieldId).toString();
                variables.put(fieldId, Double.valueOf(value));
            });
            Expression expression = new ExpressionBuilder(formula)
                    .variables(ids.toArray(new String[0]))
                    .build()
                    .setVariables(variables);
            double result = expression.evaluate();
            return new WightedIndicator(result, i);
        };
    }

    private List<WightedIndicator> getWightedIndicators(List<Indicator> indicators, Function<Indicator, WightedIndicator> mapper) {
        return indicators.stream()
                .map(mapper)
                .sorted(Comparator.comparingDouble(WightedIndicator::getWightedAmount).reversed())
                .collect(Collectors.toList());
    }

    private Function<Indicator, WightedIndicator> toWightedIndicator() {
        return i -> {
            double wightedAmount = calculateDefaultSystemWightedAmount(i);
            return new WightedIndicator(wightedAmount, i);
        };
    }

    private double calculateDefaultSystemWightedAmount(Indicator i) {
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
        validateIndicatorByDefaultRatingFields(i, fieldsIds);

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

    private void validateIndicatorByDefaultRatingFields(Indicator i, List<String> fieldsIds) {
        Map<String, FieldMetadata> fieldMetadataMap = restService.findMetadataByFieldsIds(fieldsIds);

        JSONObject jsonObject = converter.createJSONObject(i);
        fieldsIds.forEach(fieldId -> {
            String value = getDataFromField(jsonObject, fieldId).toString();
            FieldMetadata metadata = fieldMetadataMap.get(fieldId);
            validator.validateFieldByMetadata(fieldId, value, metadata);
        });
    }
}

package com.kovshar.heterogeneous.utils;

import com.kovshar.heterogeneous.model.*;
import com.kovshar.heterogeneous.repository.FieldsMetadataRepository;
import com.kovshar.heterogeneous.repository.IndicatorRepository;
import com.kovshar.heterogeneous.service.SequenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
@Slf4j
public class DBFiller {
    private static final List<String> UNIVERSITY_NAMES = List.of(
            "Національний технічний університет України Київський політехнічний інститут ім. Ігоря Сікорського",
            "Київський національний університет ім. Тараса Шевченка",
            "Сумський державний університет",
            "Національний технічний університет \"Харківський політехнічний інститут\"",
            "Національний університет \"Львівська політехніка\"",
            "Харківський національний університет ім. В.Н. Каразіна",
            "Національний університет \"Києво-Могилянська академія\"",
            "Львівський національний університет ім. Івана Франка",
            "Харківський національний університет радіоелектроніки",
            "Вінницький національний технічний університет"

    );
    private final IndicatorRepository indicatorRepository;
    private final SequenceService sequenceService;
    private final FieldsMetadataRepository fieldsMetadataRepository;

    @PostConstruct
    public void initData() {
        indicatorRepository.deleteAll();
        sequenceService.drop();
        for (int i = 0; i < UNIVERSITY_NAMES.size(); i++) {
            String name = UNIVERSITY_NAMES.get(i);
            int foreignExpertCount = random(70);
            int expertCount = random(100);
            Indicator indicator = Indicator.builder()
                    .id(sequenceService.generateSequence(Indicator.SEQUENCE_NAME))
                    .uuid(UUID.randomUUID().toString())
                    //ПЗФ
                    .organization(name)
                    .year(random(1960, 2020))
                    .branchOfScience("Technical")
                    .publicFunding(random(2_000_000L))
                    .foreignFinancing(random(1_000_000L))
                    .foreignFinancingPercentage(random(100))
                    .sourceOfForeignFunding("Foreign companies")
                    .totalBudget(random(5_000_000L))
                    .exceedingStateLevelFinancing(random(500_000L))
                    .exceedingStateLevelFinancingPercentage(random(100))
                    //ПЕФ
                    .numberOfPublicationsWithoutForeignAuthors(random(400))
                    .medianStatus1(random(200))
                    .numberOfPublications(random(1000))
                    .medianStatus3(random(500))
                    .programFounder("Head of " + name)
                    .fundingEfficiency(random(100))
                    //ПМС
                    .numberOfPublicationsWithForeignAuthors(random(1000))
                    .statusOfPublicationsWithForeignAuthors("IN PROGRESS")
                    .medianOfStatusOfPublicationWithForeignAuthors(random(500))
                    .totalNumberOfPublication(random(1000))
                    .statusOfTotalNumberOfPublication("IN PROGRESS")
                    .medianOfTotalNumberOfPublication(random(300))
                    .shareOfPublicationsWithForeignAuthorsPercentage(random(100))
                    //ПЗН
                    .numberScientistsInvolved(random(1500))
                    .involvingType("Tech debt")
                    .totalNumberScientists(random(2000))
                    .country("Ukraine")
                    .shareScientistsInvolved(random(100))
                    //ПММ
                    .numberScientistsArrived(random(100))
                    .typeStayScientistsArrived("Temporary")
                    .countryArrivedFrom("USA")
                    .arrivedScientificDegree("MASTER")
                    .numberScientistsSeconded(random(100))
                    .countrySecondedTo("USA")
                    .secondedScientificDegree("MASTER")
                    .totalScientificNumber(random(700))
                    .shareScientistsArrivedPercentage(random(100))
                    .shareScientistsSecondedPercentage(random(100))
                    .flow(random(100))
                    //ПСП
                    .program("Program " + name)
                    .participatingCountries("USA, UKRAINE")
                    .fundingAmountForProgramByOrganization(random(500))
                    .fundingAmountForProgramByNationalSources(random(700))
                    .generalBudget(random(4_000_000L))
                    .organizationFundingSharePercentage(random(100))
                    //ПДІ
                    .fundingFlowPercentage(random(100))
                    .researchInfrastructureObject("HIGHWAY")
                    .researchInfrastructureType("Infra")
                    .countryUser("Ukraine")
                    .foreignResearchersNumber(random(100))
                    .daysUsedByForeignResearchersNumber(random(90))
                    .manDaysPerYear(random(100))
                    //ПМЗ
                    .employeesInInternationalPersonnelSelectionStructuresNumber(random(200))
                    .internationalPersonnelSelectionStructuresEmployeeScientificDegree("BACHELOR")
                    .internationalStructure("MINFIN")
                    .employeesEngagedInRecruitmentTotalNumber(random(200))
                    .recruitmentEmployeeScientificDegree("BACHELOR")
                    .shareInternationalPersonnelSelectionPercentage(random(100))
                    //ПМO
                    .foreignExpertCount(foreignExpertCount)
                    .expertCount(expertCount)
                    .shareOfForeignExpert(random(100))
                    .build();

            Map<String, Object> objectHashMap = new HashMap<>();
            objectHashMap.put("correlation", 10);
            indicator.setFields(objectHashMap);

            if (i == 9) {
                Map<String, Object> fields = indicator.getFields();
                Field value = new Field(UUID.randomUUID().toString(), 100);
                Field value1 = new Field(UUID.randomUUID().toString(), 2021);
                HashMap<Object, Object> data = new HashMap<>();
                data.put("data", new Date());
                data.put("name", "Ruslan");
                data.put("id", 529440L);
                data.put("names", List.of("Alfred", "Bruce", "Cat"));
                Field value3 = new Field(UUID.randomUUID().toString(), new Field(UUID.randomUUID().toString(), data));
                fields.put("Percentage", value);
                fields.put("Year", value1);
                fields.put("Date", value3);
            }
            indicatorRepository.save(indicator);
        }
        generateFieldMetadata();
    }

    private void generateFieldMetadata() {
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

        fieldsIds.forEach(fieldId -> {
            List<Filter> filters = new ArrayList<>();
            filters.add(new Filter(ComparisionOperator.BIGGER_EQUAL, "0"));
            filters.add(new Filter(ComparisionOperator.LESS_EQUAL, "100"));
            long id = sequenceService.generateSequence(FieldMetadata.SEQUENCE_NAME);
            FieldMetadata metadata = new FieldMetadata(id, fieldId, "INT", LogicOperation.AND, filters);
            fieldsMetadataRepository.save(metadata);
        });

        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter(ComparisionOperator.EQUAL, "10"));
        long id = sequenceService.generateSequence(FieldMetadata.SEQUENCE_NAME);
        FieldMetadata metadata = new FieldMetadata(id, "fields.correlation", "INT", LogicOperation.AND, filters);
        fieldsMetadataRepository.save(metadata);
    }

    private int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private int random(int max) {
        return random(1, max);
    }

    private long random(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max + 1);
    }

    private long random(long max) {
        return random(1, max);
    }
}

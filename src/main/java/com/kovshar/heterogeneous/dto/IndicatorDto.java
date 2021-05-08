package com.kovshar.heterogeneous.dto;

import lombok.Data;

import java.util.Map;

@Data
public class IndicatorDto {
    //ПЗФ
    private String organization;
    private Integer year;
    private String branchOfScience;
    private Long publicFunding;
    private Long foreignFinancing;
    private Integer foreignFinancingPercentage;
    private String sourceOfForeignFunding;
    private Long totalBudget;
    private Long exceedingStateLevelFinancing;
    private Integer exceedingStateLevelFinancingPercentage;
    //ПЕФ
    private Integer numberOfPublicationsWithoutForeignAuthors;
    private Integer medianStatus1;
    private Integer numberOfPublications;
    private Integer medianStatus3;
    private String programFounder;
    private Integer fundingEfficiency;
    //ПМС
    private Integer numberOfPublicationsWithForeignAuthors;
    private String statusOfPublicationsWithForeignAuthors;
    private Integer medianOfStatusOfPublicationWithForeignAuthors;
    private Integer totalNumberOfPublication;
    private String statusOfTotalNumberOfPublication;
    private Integer medianOfTotalNumberOfPublication;
    private Integer shareOfPublicationsWithForeignAuthorsPercentage;
    //ПЗН
    private Integer numberScientistsInvolved;
    private String involvingType;
    private Integer totalNumberScientists;
    private String country;
    private Integer shareScientistsInvolved;
    //ПММ
    private Integer numberScientistsArrived;
    private String typeStayScientistsArrived;
    private String countryArrivedFrom;
    private String arrivedScientificDegree;
    private Integer numberScientistsSeconded;
    private String countrySecondedTo;
    private String secondedScientificDegree;
    private Integer totalScientificNumber;
    private Integer shareScientistsArrivedPercentage;
    private Integer shareScientistsSecondedPercentage;
    private Integer flow;
    //ПСП
    private String program;
    private String participatingCountries;
    private Integer fundingAmountForProgramByOrganization;
    private Integer fundingAmountForProgramByNationalSources;
    private Long generalBudget;
    private Integer organizationFundingSharePercentage;
    private Integer fundingFlowPercentage;
    //ПДІ
    private String researchInfrastructureObject;
    private String researchInfrastructureType;
    private String countryUser;
    private Integer foreignResearchersNumber;
    private Integer daysUsedByForeignResearchersNumber;
    //ПМЗ
    private Integer employeesInInternationalPersonnelSelectionStructuresNumber;
    private String internationalPersonnelSelectionStructuresEmployeeScientificDegree;
    private String internationalStructure;
    private Integer employeesEngagedInRecruitmentTotalNumber;
    private String recruitmentEmployeeScientificDegree;
    private Integer shareInternationalPersonnelSelectionPercentage;
    //Added fields
    private Map<String, Object> fields;
}

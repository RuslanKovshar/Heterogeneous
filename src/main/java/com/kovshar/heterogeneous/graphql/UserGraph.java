package com.kovshar.heterogeneous.graphql;

import com.kovshar.heterogeneous.model.Indicator;
import com.kovshar.heterogeneous.service.IndicatorService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserGraph implements BaseGraph {
    private final IndicatorService indicatorService;

    @Autowired
    public UserGraph(IndicatorService indicatorService) {
        this.indicatorService = indicatorService;
    }

    @GraphQLQuery(name = "indicator")
    public List<Indicator> user(@GraphQLArgument(name = "id") Long[] ids) {
        if (ids == null) {
            return indicatorService.findAll();
        }
        return indicatorService.getAllByIds(ids);
    }
}

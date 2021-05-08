package com.kovshar.heterogeneous.controller;

import com.kovshar.heterogeneous.dto.IndicatorDto;
import com.kovshar.heterogeneous.model.Indicator;
import com.kovshar.heterogeneous.service.IndicatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/indicator")
@RequiredArgsConstructor
@Slf4j
public class IndicatorController {
    private final IndicatorService indicatorService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Indicator createIndicator(@RequestBody IndicatorDto indicatorDto) {
        log.debug("Create indicator {}", indicatorDto);
        Indicator indicator = indicatorService.saveIndicator(indicatorDto);
        log.debug("Created indicator {}", indicator);
        return indicator;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Indicator updateIndicator(@RequestBody Indicator indicator) {
        log.debug("Update indicator {}", indicator);
        Indicator saveIndicator = indicatorService.saveIndicator(indicator);
        log.debug("Updated indicator {}", saveIndicator);
        return saveIndicator;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Indicator> getIndicators() {
        log.debug("Get all indicators");
        List<Indicator> all = indicatorService.findAll();
        log.debug("Get indicators {}", all);
        return all;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Indicator getIndicator(@PathVariable Long id) {
        log.debug("Get indicator by id {}", id);
        Indicator indicator = indicatorService.getById(id);
        log.debug("Got indicator {}", indicator);
        return indicator;
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Indicator deleteIndicator(@PathVariable Long id) {
        log.debug("Delete indicator by id {}", id);
        Indicator indicator = indicatorService.deleteById(id);
        log.debug("Deleted indicator {}", indicator);
        return indicator;
    }
}

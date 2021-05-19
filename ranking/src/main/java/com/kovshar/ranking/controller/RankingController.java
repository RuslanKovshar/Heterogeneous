package com.kovshar.ranking.controller;

import com.kovshar.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingController {
    private final RankingService rankingService;

    @GetMapping("/default")
    public Object createDefaultRanking() {
        return rankingService.createDefaultSystemRanking();
    }
}

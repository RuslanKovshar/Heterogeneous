package com.kovshar.ranking.controller;

import com.kovshar.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingController {
    private final RankingService rankingService;

    @GetMapping("/default")
    public Object createDefaultRanking() {
        return rankingService.createDefaultSystemRanking();
    }

    @PostMapping("/user")
    public Object createUserRanking(@RequestBody String formula) {
        return rankingService.createUserRanking(formula);
    }
}

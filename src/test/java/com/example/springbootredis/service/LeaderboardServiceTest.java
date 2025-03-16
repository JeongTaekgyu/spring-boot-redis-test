package com.example.springbootredis.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LeaderboardServiceTest {
    @Autowired
    private LeaderboardService leaderboardService;

    @Test
    void leaderboardOperationsTest() {
        // Given - 실행 준비
        String userId = "user1";
        double score = 100.0;

        // When - 진행
        leaderboardService.addScore(userId, score);
        List<String> topPlayers = leaderboardService.getTopPlayers(1);
        Long rank = leaderboardService.getUserRank(userId);

        // Then - 결과 검증
        assertFalse(topPlayers.isEmpty());
        assertEquals(userId, topPlayers.get(0));
        assertEquals(0L, rank); // 첫 번째 순위 (0-based index)
    }
}
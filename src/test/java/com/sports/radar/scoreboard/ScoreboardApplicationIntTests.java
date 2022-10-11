package com.sports.radar.scoreboard;

import com.sports.radar.scoreboard.repository.ScoreboardRepository;
import com.sports.radar.scoreboard.service.impl.ScoreboardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class ScoreboardApplicationIntTests {

    @Autowired
    private ScoreboardServiceImpl scoreboardService;

    @Autowired
    private ScoreboardRepository scoreboardRepository;

}

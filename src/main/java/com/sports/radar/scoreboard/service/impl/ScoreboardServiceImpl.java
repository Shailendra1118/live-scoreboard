package com.sports.radar.scoreboard.service.impl;

import com.sports.radar.scoreboard.model.Game;
import com.sports.radar.scoreboard.model.GameState;
import com.sports.radar.scoreboard.model.Team;
import com.sports.radar.scoreboard.repository.ScoreboardRepository;
import com.sports.radar.scoreboard.service.ScoreboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class ScoreboardServiceImpl implements ScoreboardService {

    @Autowired
    private ScoreboardRepository scoreboardRepository;

    @Override
    public Game startGame(Team home, Team away) {
        log.info("Starting new game with home team: {} and away team: {}", home, away);
        Game newGame = Game.builder().homeTeam(home).awayTeam(away)
                .currentStatus(GameState.STARTED).build();
        return this.scoreboardRepository.addGame(newGame);
    }

    @Override
    public Game updateScore(Team homeTeam, int homeTeamScore, Team awayTeam, int awayTeamScore) {
        return this.scoreboardRepository.updateScore(homeTeam, homeTeamScore, awayTeam, awayTeamScore);
    }

    @Override
    public Game finishGame(Team homeTeam, Team awayTeam) {
        return this.scoreboardRepository.updateStatus(homeTeam, awayTeam, GameState.FINISHED);
    }

    @Override
    public List<Game> getSummary() {
        return this.scoreboardRepository.getAllGames();
    }

    @Override
    public List<Game> getAllGames() {
        return this.scoreboardRepository.getAllGames();
    }
}

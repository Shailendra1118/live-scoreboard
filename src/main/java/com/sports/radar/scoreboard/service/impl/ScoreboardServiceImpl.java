package com.sports.radar.scoreboard.service.impl;

import com.sports.radar.scoreboard.model.Game;
import com.sports.radar.scoreboard.model.GameState;
import com.sports.radar.scoreboard.model.Team;
import com.sports.radar.scoreboard.repository.ScoreboardRepository;
import com.sports.radar.scoreboard.service.ScoreboardService;
import com.sports.radar.scoreboard.utils.SummaryComparator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class ScoreboardServiceImpl implements ScoreboardService {

    @Autowired
    private ScoreboardRepository scoreboardRepository;

    @Override
    public Game startGame(Team home, Team away, LocalDateTime time) {
        log.info("Starting new game with home team: {} and away team: {}", home, away);
        Game newGame = Game.builder().homeTeam(home).awayTeam(away)
                .currentStatus(GameState.STARTED).startTime(time).build();
        return this.scoreboardRepository.addGame(newGame);
    }

    @Override
    public Game updateScore(Team homeTeam, int homeTeamScore, Team awayTeam, int awayTeamScore) {
        return this.scoreboardRepository.updateScore(homeTeam, homeTeamScore, awayTeam, awayTeamScore);
    }

    @Override
    public Game finishGame(Team homeTeam, Team awayTeam) {
        Game finishedGame = this.scoreboardRepository.updateStatus(homeTeam, awayTeam, GameState.FINISHED);
        //move this match out from scoreboard
        if(this.scoreboardRepository.removeFromBoard(finishedGame)) {
            //save it for history
            this.scoreboardRepository.getAllGames().add(finishedGame);
        }
        return finishedGame;
    }

    @Override
    public List<Game> getScoreboardGames() {
        List<Game> ongoingGames = this.scoreboardRepository.getScorecard();
        Collections.sort(ongoingGames, new SummaryComparator());
        return ongoingGames;
    }

    @Override
    public List<Game> getAllGames() {
        return this.scoreboardRepository.getAllGames();
    }
}

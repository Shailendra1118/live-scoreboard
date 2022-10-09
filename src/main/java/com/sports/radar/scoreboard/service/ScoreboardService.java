package com.sports.radar.scoreboard.service;

import com.sports.radar.scoreboard.model.Game;
import com.sports.radar.scoreboard.model.Team;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ScoreboardService {

    Game startGame(Team home, Team away);

    void updateScore(Team homeTeam, int homeTeamScore, Team awayTeam, int awayTeamScore);

    void finishGame(Team home, Team away);

    List<Game> getSummary();

    List<Game> getAllGames();


}
package com.sports.radar.scoreboard.repository;

import com.sports.radar.scoreboard.model.Game;
import com.sports.radar.scoreboard.model.GameState;
import com.sports.radar.scoreboard.model.Team;

import java.util.List;

public interface ScoreboardRepository {

    Game addGame(Game game);

    boolean updateScore(Team homeTeam, int homeTeamScore, Team awayTeam, int awayTeamScore);

    void updateStatus(Team homeTeam, Team awayTeam, GameState status);

    List<Game> getAllGames();
}

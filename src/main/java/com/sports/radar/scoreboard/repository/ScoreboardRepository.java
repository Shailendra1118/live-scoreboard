package com.sports.radar.scoreboard.repository;

import com.sports.radar.scoreboard.model.Game;
import com.sports.radar.scoreboard.model.GameState;
import com.sports.radar.scoreboard.model.Team;

import java.util.List;

public interface ScoreboardRepository {

    Game addGame(Game game);

    List<Game> addGames(List<Game> games);

    Game updateScore(Team homeTeam, int homeTeamScore, Team awayTeam, int awayTeamScore);

    Game updateStatus(Team homeTeam, Team awayTeam, GameState status);

    List<Game> getAllGames();
}

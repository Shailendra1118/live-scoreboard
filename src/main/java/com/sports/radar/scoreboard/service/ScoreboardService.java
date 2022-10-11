package com.sports.radar.scoreboard.service;

import com.sports.radar.scoreboard.model.Game;
import com.sports.radar.scoreboard.model.Team;

import java.time.LocalDateTime;
import java.util.List;

public interface ScoreboardService {

    /**
     * Start the new game with default score 0-0 for both home and away team
     * @param home
     * @param away
     * @param start-time of the game
     * @return new game
     */
    Game startGame(Team home, Team away, LocalDateTime time);

    /**
     * Update given score for teams
     * @param homeTeam
     * @param homeTeamScore
     * @param awayTeam
     * @param awayTeamScore
     * @return updated game
     */
    Game updateScore(Team homeTeam, int homeTeamScore, Team awayTeam, int awayTeamScore);

    /**
     * Finish game and removes it from current scoreboard and put it in reporting collection
     * @param home
     * @param away
     * @return just finished game
     */
    Game finishGame(Team home, Team away);

    /**
     * Get ongoing games of scoreboard
     * @return
     */
    List<Game> getScoreboardGames();

    /**
     * List all past finished games
     * @return
     */
    List<Game> getAllGames();


}

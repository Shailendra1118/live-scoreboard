package com.sports.radar.scoreboard.repository;

import com.sports.radar.scoreboard.model.Game;
import com.sports.radar.scoreboard.model.GameState;
import com.sports.radar.scoreboard.model.Team;

import java.util.List;

public interface ScoreboardRepository {

    /**
     * Adds a new game to scoreboard
     * @param game
     * @return added game
     */
    Game addGame(Game game);

    /**
     * Utility method to add more games at one go
     * @param games
     * @return List of added games
     */
    List<Game> addGames(List<Game> games);

    /**
     * Update scores for home and away teams
     * @param homeTeam
     * @param homeTeamScore
     * @param awayTeam
     * @param awayTeamScore
     * @return Game with upated score
     */
    Game updateScore(Team homeTeam, int homeTeamScore, Team awayTeam, int awayTeamScore);

    /**
     * This method updates status of match like STARTED, HALTED or FINISHED
     * Enum GameState can be enhanced with more status
     * @param homeTeam
     * @param awayTeam
     * @param state
     * @return Updated game
     */
    Game updateStatus(Team homeTeam, Team awayTeam, GameState state);

    /**
     * This method removes the Finished game from the running scoreboard and puts in collection
     * allGames for reporting purpose
     * @param game
     * @return true or false based on success
     */
    boolean removeFromBoard(Game game);

    /**
     * Retrieves all past games, this does not include ongoing games listed in scoreboard currently
     * @return
     */
    List<Game> getAllGames();

    /**
     * Get summary of current scoreboard
     * @return
     */
    List<Game> getScorecard();
}

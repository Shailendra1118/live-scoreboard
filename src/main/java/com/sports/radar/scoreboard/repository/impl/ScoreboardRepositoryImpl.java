package com.sports.radar.scoreboard.repository.impl;

import com.sports.radar.scoreboard.model.Game;
import com.sports.radar.scoreboard.model.GameState;
import com.sports.radar.scoreboard.model.Team;
import com.sports.radar.scoreboard.repository.ScoreboardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class ScoreboardRepositoryImpl implements ScoreboardRepository {

    //TODO future enhancements
    /**
     * Here we work with DB data objects that further needs to be mapped to Business objects (in service layer)
     * For simplicity we are keeping Game class object same as corresponding DB object
     */

    /**
     * This collection represents all ongoing matches aka running scoreboard
     */
    private List<Game> scoreboard = new ArrayList<>();

    /**
     * This collection contains all finished previous games played
     */
    private List<Game> allGames = new ArrayList<>();

    @Override
    public Game addGame(Game game) {
        this.scoreboard.add(game);
        return game;
    }

    @Override
    public List<Game> addGames(List<Game> games) {
        this.scoreboard.addAll(games);
        return games;
    }

    @Override
    public Game updateScore(Team homeTeam, int homeTeamScore, Team awayTeam, int awayTeamScore) {
        Optional<Game> gameOpt = this.findOngoingGameForTeams(homeTeam, awayTeam);
        if(gameOpt.isPresent()) {
            Game currentGame = gameOpt.get();
            currentGame.setHomeTeamScore(homeTeamScore);
            currentGame.setAwayTeamScore(awayTeamScore);
            return currentGame;
        }else {
            throw new IllegalArgumentException("No on-going match with these teams.");
        }
    }

    @Override
    public Game updateStatus(Team homeTeam, Team awayTeam, GameState state) {
        Game currentGame = this.findOngoingGameForTeams(homeTeam, awayTeam).orElseThrow(IllegalArgumentException :: new);
        currentGame.setEndTime(LocalDateTime.now());
        currentGame.setCurrentStatus(state);
        return currentGame;
    }

    @Override
    public boolean removeFromBoard(Game game) {
        return this.scoreboard.removeIf(g -> g.getHomeTeam().getName()
                .equals(game.getHomeTeam().getName()) && g.getAwayTeam().getName().equals(game.getAwayTeam().getName()));
    }

    @Override
    public List<Game> getAllGames() {
        return this.allGames;
    }

    @Override
    public List<Game> getScorecard() {
        return this.scoreboard;
    }

    public Optional<Game> findOngoingGameForTeams(Team homeTeam, Team awayTeam) {
        return this.scoreboard.stream()
                .filter(g -> g.getCurrentStatus().equals(GameState.STARTED)) //match could be halted state
                .filter(g -> g.getHomeTeam().getName().equals(homeTeam.getName())
                        && g.getAwayTeam().getName().equals(awayTeam.getName()))
                .findFirst();
    }

    public Optional<Game> findGameFromScoreboard(Game game) {
        return this.scoreboard.stream()
                .filter(g -> g.getHomeTeam().getName().equals(game.getHomeTeam().getName())
                        && g.getAwayTeam().getName().equals(game.getAwayTeam().getName()))
                .findFirst();
    }
}

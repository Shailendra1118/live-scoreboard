package com.sports.radar.scoreboard.repository.impl;

import com.sports.radar.scoreboard.model.Game;
import com.sports.radar.scoreboard.model.GameState;
import com.sports.radar.scoreboard.model.Team;
import com.sports.radar.scoreboard.repository.ScoreboardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ScoreboardRepositoryImpl implements ScoreboardRepository {

    //in-memory data
    private List<Game> scoreboard = new ArrayList<>();

    @Override
    public Game addGame(Game game) {
        this.scoreboard.add(game);
        return game;
    }

    @Override
    public boolean updateScore(Team homeTeam, int homeTeamScore, Team awayTeam, int awayTeamScore) {
        Optional<Game> gameOpt = this.findOngoingGameForTeams(homeTeam, awayTeam);
        if(gameOpt.isPresent()) {
            Game currentGame = gameOpt.get();
            currentGame.setHomeTeamScore(homeTeamScore);
            currentGame.setAwayTeamScore(awayTeamScore);
            return true;
        }else {
            throw new IllegalArgumentException("No on-going match with these teams.");
        }
    }

    @Override
    public void updateStatus(Team homeTeam, Team awayTeam, GameState status) {
        Game currentGame = this.findOngoingGameForTeams(homeTeam, awayTeam).orElseThrow(IllegalArgumentException :: new);
        currentGame.setCurrentStatus(status);
    }

    @Override
    public List<Game> getAllGames() {
        return this.scoreboard;
    }

    public Optional<Game> findOngoingGameForTeams(Team homeTeam, Team awayTeam) {
        return this.scoreboard.stream()
                .filter(g -> g.getCurrentStatus().equals(GameState.STARTED))
                .filter(g -> g.getHomeTeam().getName().equals(homeTeam.getName())
                        && g.getAwayTeam().getName().equals(awayTeam.getName()))
                .findFirst();
    }
}
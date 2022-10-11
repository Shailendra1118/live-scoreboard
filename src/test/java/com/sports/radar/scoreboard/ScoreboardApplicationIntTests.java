package com.sports.radar.scoreboard;

import com.sports.radar.scoreboard.model.Game;
import com.sports.radar.scoreboard.model.GameState;
import com.sports.radar.scoreboard.model.Team;
import com.sports.radar.scoreboard.model.TeamType;
import com.sports.radar.scoreboard.service.ScoreboardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains integration level Tests
 */
@SpringBootTest
public class ScoreboardApplicationIntTests {

    @Autowired
    private ScoreboardService scoreboardService;

    @Test
    public void testStartGameWithLouveAll() {
        Team homeTeam  = getNewTeam("Denmark", TeamType.HOME_TEAM);
        Team awayTeam = getNewTeam("Norway", TeamType.AWAY_TEAM);

        Game newGame = scoreboardService.startGame(homeTeam, awayTeam, LocalDateTime.now());
        assertTrue(newGame.getHomeTeamScore() == 0);
        assertTrue(newGame.getAwayTeamScore() == 0);
        assertTrue(scoreboardService.getScoreboardGames().size() == 1);
    }

    @Test
    public void testStartGameWithStartedStatus() {
        Team homeTeam  = getNewTeam("Denmark", TeamType.HOME_TEAM);
        Team awayTeam = getNewTeam("Norway", TeamType.AWAY_TEAM);

        Game newGame = scoreboardService.startGame(homeTeam, awayTeam, LocalDateTime.now());
        assertTrue(newGame.getCurrentStatus() == GameState.STARTED);
    }

    @Test
    public void testUpdateScoreOfUnknownGame() {
        Team homeTeam  = getNewTeam("Austria", TeamType.HOME_TEAM);
        Team awayTeam = getNewTeam("Spain", TeamType.AWAY_TEAM);

        Exception exp = assertThrows(IllegalArgumentException.class,
                () -> scoreboardService.updateScore(homeTeam, 4, awayTeam, 1));
        String expectedMessage = "No on-going match with these teams.";
        assertTrue(exp.getMessage().contains(expectedMessage));

    }

    @Test
    public void testFinishGame() {
        Team homeTeam  = getNewTeam("Austria", TeamType.HOME_TEAM);
        Team awayTeam = getNewTeam("Spain", TeamType.AWAY_TEAM);

        scoreboardService.startGame(homeTeam, awayTeam, LocalDateTime.now());
        assertTrue(scoreboardService.getScoreboardGames().size() == 1);
        Game finishedGame = scoreboardService.finishGame(homeTeam, awayTeam);
        assertTrue(finishedGame.getCurrentStatus().equals(GameState.FINISHED));
        // should move the finished from scoreboard to all-games history collection
        assertTrue(scoreboardService.getScoreboardGames().size() == 0);
        assertTrue(scoreboardService.getAllGames().size() == 1);
    }

    @Test
    public void testSummaryOrder() {

        Team homeTeam1  = getNewTeam("Japan", TeamType.HOME_TEAM);
        Team awayTeam1 = getNewTeam("USA", TeamType.AWAY_TEAM);
        scoreboardService.startGame(homeTeam1, awayTeam1, LocalDateTime.now());
        scoreboardService.updateScore(homeTeam1, 1, awayTeam1, 0);

        Team homeTeam2  = getNewTeam("Vietnam", TeamType.HOME_TEAM);
        Team awayTeam2 = getNewTeam("Singapore", TeamType.AWAY_TEAM);
        scoreboardService.startGame(homeTeam2, awayTeam2, LocalDateTime.now());
        scoreboardService.updateScore(homeTeam2, 6, awayTeam2, 5);

        Team homeTeam3  = getNewTeam("Ireland", TeamType.HOME_TEAM);
        Team awayTeam3 = getNewTeam("Scotland", TeamType.AWAY_TEAM);
        scoreboardService.startGame(homeTeam3, awayTeam3, LocalDateTime.now());
        scoreboardService.updateScore(homeTeam3, 0, awayTeam3, 0);

        assertTrue(scoreboardService.getScoreboardGames().size() == 3);
        String firstTeam = "Vietnam 6 - Singapore 5";
        String secTeam = "Japan 1 - USA 0";
        String thirdTeam = "Ireland 0 - Scotland 0";
        assertTrue(scoreboardService.getScoreboardGames().get(0).getSummary().equals(firstTeam));
        assertTrue(scoreboardService.getScoreboardGames().get(1).getSummary().equals(secTeam));
        assertTrue(scoreboardService.getScoreboardGames().get(2).getSummary().equals(thirdTeam));

    }

    @Test
    public void testSummaryOrderForTeamsWithSameCumulativeScores() {

        LocalDate date = LocalDate.of(2022, 10, 12);
        LocalTime time1 = LocalTime.of(05, 34);
        LocalTime time2 = LocalTime.of(06, 00);

        LocalDateTime localDateTime1 = LocalDateTime.of(date, time1);
        LocalDateTime localDateTime2 = LocalDateTime.of(date, time2);

        Team homeTeam1  = getNewTeam("Japan", TeamType.HOME_TEAM);
        Team awayTeam1 = getNewTeam("USA", TeamType.AWAY_TEAM);
        scoreboardService.startGame(homeTeam1, awayTeam1, localDateTime1);
        scoreboardService.updateScore(homeTeam1, 0, awayTeam1, 0);

        Team homeTeam2  = getNewTeam("Vietnam", TeamType.HOME_TEAM);
        Team awayTeam2 = getNewTeam("Singapore", TeamType.AWAY_TEAM);
        scoreboardService.startGame(homeTeam2, awayTeam2, localDateTime2);
        scoreboardService.updateScore(homeTeam2, 0, awayTeam2, 0);

        String firstTeam = "Vietnam 0 - Singapore 0";
        String secTeam = "Japan 0 - USA 0";
        assertTrue(scoreboardService.getScoreboardGames().size() == 2);
        assertTrue(scoreboardService.getScoreboardGames().get(0).getSummary().equals(firstTeam));
        assertTrue(scoreboardService.getScoreboardGames().get(1).getSummary().equals(secTeam));

    }


    private Game getNewGame(Team teamA, Team teamB, int teamAScore, int teamBScore) {
        return Game.builder().homeTeam(teamA).homeTeamScore(teamAScore).awayTeam(teamB).awayTeamScore(teamBScore)
                .currentStatus(GameState.STARTED)
                .startTime(LocalDateTime.now()).build();
    }

    private Team getNewTeam(String name, TeamType playingAs) {
        return Team.builder().name(name).playingAs(playingAs).build();
    }


}

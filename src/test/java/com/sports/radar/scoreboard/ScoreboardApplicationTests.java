package com.sports.radar.scoreboard;

import com.sports.radar.scoreboard.model.Game;
import com.sports.radar.scoreboard.model.GameState;
import com.sports.radar.scoreboard.model.Team;
import com.sports.radar.scoreboard.model.TeamType;
import com.sports.radar.scoreboard.repository.ScoreboardRepository;
import com.sports.radar.scoreboard.service.impl.ScoreboardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

/**
 * This class contains Unit Tests or ScoreboardService
 */

@SpringBootTest
class ScoreboardApplicationTests {

	@InjectMocks
	private ScoreboardServiceImpl scoreboardService;

	@Mock
	private ScoreboardRepository scoreboardRepository;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testNewGameInitialScores() {
		Team homeTeam  = getNewTeam("Man United", TeamType.HOME_TEAM);
		Team awayTeam = getNewTeam("Man City", TeamType.AWAY_TEAM);
		Game newGame = getNewGame(homeTeam, awayTeam, 0, 0);
		Mockito.when(scoreboardRepository.addGame(any())).thenReturn(newGame);

		Game currentGame = scoreboardService.startGame(homeTeam, awayTeam, LocalDateTime.now());
		assertTrue(currentGame.getHomeTeamScore() == 0);
		assertTrue(currentGame.getAwayTeamScore() == 0);
	}

	@Test
	public void testNewGameAdded() {
		Team homeTeam = getNewTeam("Man United", TeamType.HOME_TEAM);
		Team awayTeam = getNewTeam("Man City", TeamType.AWAY_TEAM);
		Game newGame = getNewGame(homeTeam, awayTeam, 0, 0);
		Mockito.when(scoreboardRepository.addGame(any())).thenReturn(newGame);

		Game currentGame = scoreboardService.startGame(homeTeam, awayTeam, LocalDateTime.now());
		assertTrue(currentGame.getCurrentStatus() == GameState.STARTED);
	}

	@Test
	public void testScoreUpdated() {
		Team homeTeam = getNewTeam("Man United", TeamType.HOME_TEAM);
		Team awayTeam = getNewTeam("Man City", TeamType.AWAY_TEAM);
		Game newGame = getNewGame(homeTeam, awayTeam, 0, 0);
		Mockito.when(scoreboardRepository.addGame(any())).thenReturn(newGame);
		Game expected = Game.builder().homeTeamScore(5).awayTeamScore(2).build();
		Mockito.when(scoreboardRepository.updateScore(any(), anyInt(), any(), anyInt())).thenReturn(expected);

		Game game = scoreboardService.updateScore(homeTeam, 5, awayTeam, 2);
		assertTrue(game.getHomeTeamScore() == 5);
		assertTrue(game.getAwayTeamScore() == 2);
	}

	@Test
	public void testFinishGame() {
		Team homeTeam = getNewTeam("Man United", TeamType.HOME_TEAM);
		Team awayTeam = getNewTeam("Man City", TeamType.AWAY_TEAM);
		Game expectedGame = getNewGame(homeTeam, awayTeam, 0, 0);
		expectedGame.setCurrentStatus(GameState.FINISHED);
		Mockito.when(scoreboardRepository.updateStatus(any(), any(), any())).thenReturn(expectedGame);
		Mockito.when(scoreboardRepository.removeFromBoard(any())).thenReturn(true);

		Game game = scoreboardService.finishGame(homeTeam, awayTeam);
		assertTrue(game.getCurrentStatus() == GameState.FINISHED);
		assertTrue(scoreboardService.getScoreboardGames().size() == 0);

	}


	@Test
	public void testGetAllGames() {
		Team homeTeam1 = getNewTeam("Man United", TeamType.HOME_TEAM);
		Team awayTeam1 = getNewTeam("Man City", TeamType.AWAY_TEAM);
		Game newGame1 = getNewGame(homeTeam1, awayTeam1, 0, 0);

		Team homeTeam2 = getNewTeam("Chelsea", TeamType.HOME_TEAM);
		Team awayTeam2 = getNewTeam("Munich", TeamType.AWAY_TEAM);
		Game newGame2 = getNewGame(homeTeam2, awayTeam2, 0, 0);

		Team homeTeam3 = getNewTeam("Real Madrid", TeamType.HOME_TEAM);
		Team awayTeam3 = getNewTeam("Barcelona", TeamType.AWAY_TEAM);
		Game newGame3 = getNewGame(homeTeam3, awayTeam3, 0, 0);

		List<Game> expectedGames = new ArrayList<>();
		expectedGames.addAll(List.of(newGame1, newGame2, newGame3));

		Mockito.when(scoreboardRepository.getAllGames()).thenReturn(expectedGames);

		List<Game> scoreboard = scoreboardService.getAllGames();
		assertTrue(scoreboard.size() == 3);
		assertTrue(scoreboard.get(0).getCurrentStatus() == GameState.STARTED);
	}

	@Test
	public void testSummaryDetails() {
		Team homeTeam1 = getNewTeam("Mexico", TeamType.HOME_TEAM);
		Team awayTeam1 = getNewTeam("Canada", TeamType.AWAY_TEAM);
		Game game1 = getNewGame(homeTeam1, awayTeam1, 2, 3);

		Team homeTeam2 = getNewTeam("Spain", TeamType.HOME_TEAM);
		Team awayTeam2 = getNewTeam("Brazil", TeamType.AWAY_TEAM);
		Game game2 = getNewGame(homeTeam2, awayTeam2, 4, 5);

		List<Game> expectedGames = new ArrayList<>();
		expectedGames.add(game1);
		expectedGames.add(game2);

		Mockito.when(scoreboardRepository.getScorecard()).thenReturn(expectedGames);
		List<Game> scoreboard = scoreboardService.getScoreboardGames();
		assertTrue(scoreboard.size() == 2);
		assertTrue(expectedGames.get(0).getHomeTeam().getName().equals("Spain"));
		assertTrue(expectedGames.get(0).getAwayTeam().getName().equals("Brazil"));


	}

	@Test void testSummaryOrderWithSameCumulativeScore() {

		LocalDate date = LocalDate.of(2022, 10, 12);
		LocalTime time1 = LocalTime.of(10, 34);
		LocalTime time2 = LocalTime.of(10, 33);

		LocalDateTime localDateTime1 = LocalDateTime.of(date, time1);
		LocalDateTime localDateTime2 = LocalDateTime.of(date, time2);

		Team homeTeam1 = getNewTeam("Mexico", TeamType.HOME_TEAM);
		Team awayTeam1 = getNewTeam("Canada", TeamType.AWAY_TEAM);
		Game game1 = getNewGame(homeTeam1, awayTeam1, 2, 3);
		game1.setStartTime(localDateTime1);

		Team homeTeam2 = getNewTeam("Spain", TeamType.HOME_TEAM);
		Team awayTeam2 = getNewTeam("Brazil", TeamType.AWAY_TEAM);
		Game game2 = getNewGame(homeTeam2, awayTeam2, 4, 5);

		Team homeTeam3 = getNewTeam("Ukraine", TeamType.HOME_TEAM);
		Team awayTeam3 = getNewTeam("Russia", TeamType.AWAY_TEAM);
		Game game3= getNewGame(homeTeam3, awayTeam3, 3, 2);
		game3.setStartTime(localDateTime2);

		List<Game> expectedGames = new ArrayList<>();
		expectedGames.add(game1);
		expectedGames.add(game2);
		expectedGames.add(game3);

		Mockito.when(scoreboardRepository.getScorecard()).thenReturn(expectedGames);
		List<Game> scoreboard = scoreboardService.getScoreboardGames();
		assertTrue(scoreboard.size() == 3);

		assertTrue(expectedGames.get(1).getHomeTeam().getName().equals("Mexico"));
		assertTrue(expectedGames.get(1).getAwayTeam().getName().equals("Canada"));
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

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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

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
	public void testNewGameScores() {
		System.out.println("testStartedGame!");
		Team homeTeam  = Team.builder().name("PSG").playingAs(TeamType.HOME_TEAM).build();
		Team awayTeam = Team.builder().name("Barcelona").playingAs(TeamType.AWAY_TEAM).build();
		Game newGame = Game.builder().homeTeam(homeTeam).awayTeam(awayTeam)
				.currentStatus(GameState.STARTED).build();
		Mockito.when(scoreboardRepository.addGame(any())).thenReturn(newGame);
		//Mockito.when(scoreboardRepository.getAllGames()).thenReturn(List.of(newGame));

		Game currentGame = scoreboardService.startGame(homeTeam, awayTeam);
		assertTrue(currentGame.getHomeTeamScore() == 0);
		assertTrue(currentGame.getAwayTeamScore() == 0);


	}

	@Test
	public void testNewGameAdded() {
		Team homeTeam = Team.builder().name("Man United").playingAs(TeamType.HOME_TEAM).build();
		Team awayTeam = Team.builder().name("Man City").playingAs(TeamType.AWAY_TEAM).build();
		Game newGame = Game.builder().homeTeam(awayTeam).awayTeam(awayTeam)
				.currentStatus(GameState.STARTED).build();
		Mockito.when(scoreboardRepository.addGame(any())).thenReturn(newGame);
		Mockito.when(scoreboardRepository.getAllGames()).thenReturn(List.of(newGame));

		Game currentGame = scoreboardService.startGame(homeTeam, awayTeam);
		assertTrue(currentGame.getCurrentStatus() == GameState.STARTED);
		assertTrue(scoreboardService.getAllGames().size() == 1);
	}

	@Test
	public void testGameUpdated() {
		Team homeTeam = Team.builder().name("Man United").playingAs(TeamType.HOME_TEAM).build();
		Team awayTeam = Team.builder().name("Man City").playingAs(TeamType.AWAY_TEAM).build();
		Game newGame = Game.builder().homeTeam(awayTeam).awayTeam(awayTeam)
				.currentStatus(GameState.STARTED).build();
		Mockito.when(scoreboardRepository.addGame(any())).thenReturn(newGame);
		Mockito.when(scoreboardRepository.getAllGames()).thenReturn(List.of(newGame));
		Mockito.when(scoreboardRepository.updateScore(any(), anyInt(), any(), anyInt())).thenReturn(true);

		scoreboardService.updateScore(homeTeam, 0, awayTeam, 3);
		assertTrue(scoreboardService.getAllGames().get(0).getHomeTeamScore() == 0);
		assertTrue(scoreboardService.getAllGames().get(0).getAwayTeamScore() == 3);
	}

}

package com.sports.radar.scoreboard.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Game {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int homeTeamScore;
    private int awayTeamScore;
    private Team homeTeam;
    private Team awayTeam;
    private GameState currentStatus;
    private boolean isRemoved;

}

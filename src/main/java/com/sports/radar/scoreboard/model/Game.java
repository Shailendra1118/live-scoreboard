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

    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        return sb.append(this.homeTeam.getName()).append(" ").append(this.homeTeamScore).append(" - ")
                .append(this.awayTeam.getName()).append(" ").append(this.awayTeamScore).toString();
    }

}

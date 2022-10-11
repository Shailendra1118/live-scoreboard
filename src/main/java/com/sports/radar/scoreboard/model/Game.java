package com.sports.radar.scoreboard.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Comparator;

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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append(this.homeTeam).append(" ").append(this.homeTeamScore).append(" - ")
                .append(this.awayTeam).append(" ").append(this.awayTeamScore).toString();
    }


    class SortByScore implements Comparator<Game> {
        public int compare(Game gameA, Game gameB) {
            int gameATotalScore = gameA.getHomeTeamScore() + gameA.getAwayTeamScore();
            int gameBTotalScore = gameB.getHomeTeamScore() + gameB.getAwayTeamScore();
            if(gameATotalScore == gameBTotalScore) {
                if(gameA.startTime.isAfter(gameB.getStartTime()))
                    return -1;
                else if(gameA.startTime.isAfter(gameB.getStartTime()))
                    return +1;
                return 0;
            }else
                return gameBTotalScore - gameATotalScore;
        }
    }

}

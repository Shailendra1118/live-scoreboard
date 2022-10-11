package com.sports.radar.scoreboard.utils;

import com.sports.radar.scoreboard.model.Game;

import java.util.Comparator;

public class SummaryComparator implements Comparator<Game> {
    public int compare(Game gameA, Game gameB) {
        int gameATotalScore = gameA.getHomeTeamScore() + gameA.getAwayTeamScore();
        int gameBTotalScore = gameB.getHomeTeamScore() + gameB.getAwayTeamScore();
        if(gameATotalScore == gameBTotalScore) {
            if(gameA.getStartTime().isAfter(gameB.getStartTime()))
                return -1;
            else if(gameA.getStartTime().isAfter(gameB.getStartTime()))
                return +1;
            return 0;
        }else
            return gameBTotalScore - gameATotalScore;
    }
}

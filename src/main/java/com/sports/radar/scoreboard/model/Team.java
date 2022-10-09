package com.sports.radar.scoreboard.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Team {

    private String name;
    private TeamType playingAs;

}

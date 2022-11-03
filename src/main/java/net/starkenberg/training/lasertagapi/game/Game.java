package net.starkenberg.training.lasertagapi.game;

import lombok.Data;
import net.starkenberg.training.lasertagapi.team.Team;

@Data
public class Game {
    private Team team1;
    private Team team2;
}

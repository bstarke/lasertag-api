package net.starkenberg.training.lasertagapi.team;


import lombok.Data;
import net.starkenberg.training.lasertagapi.player.Player;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
public class Team {
    private String name;
    private int score;
    List<Player> players;
}

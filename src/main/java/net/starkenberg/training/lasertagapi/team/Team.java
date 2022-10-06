package net.starkenberg.training.lasertagapi.team;


import lombok.Data;
import net.starkenberg.training.lasertagapi.player.Player;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
@Entity
public class Team {
    @Id
    @Column(unique = true)
    private Long id;
    @Column(length = 30, unique = true)
    private String name;
    @ManyToMany
    List<Player> players;
}

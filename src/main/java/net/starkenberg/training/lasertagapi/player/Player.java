package net.starkenberg.training.lasertagapi.player;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Player {
    @Id
    @Column(unique = true)
    private Long id;
    @Column(name="codename", length = 30, unique = true)
    private String codeName;
}

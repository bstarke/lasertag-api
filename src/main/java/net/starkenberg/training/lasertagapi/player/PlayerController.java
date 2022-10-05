package net.starkenberg.training.lasertagapi.player;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Comparator;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/players")
public class PlayerController {
    private final PlayerRepository repo;

    public PlayerController(PlayerRepository repository) {
        this.repo = repository;
    }

    @GetMapping
    public ResponseEntity<List<Player>> getPlayers() {
        List<Player> players = repo.findAll();
        players.sort(Comparator.comparing(Player::getId));
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Player> getById(@PathVariable String id) {
        return new ResponseEntity<>(repo.getReferenceById(Long.valueOf(id)),HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        repo.deleteById(Long.valueOf(id));
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Player> newPlayer(@RequestBody Player player, UriComponentsBuilder uriBuilder) {
        player = repo.save(player);
        return ResponseEntity.created(
                uriBuilder.path("/players/{id}").buildAndExpand(player.getId()).toUri()).build();
    }

}

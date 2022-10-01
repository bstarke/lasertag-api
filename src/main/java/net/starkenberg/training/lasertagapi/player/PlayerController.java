package net.starkenberg.training.lasertagapi.player;

import lombok.Data;
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

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/players")
public class PlayerController {
    private final PlayerRepository service;

    public PlayerController(PlayerRepository service) {
        this.service = service;
    }

    @GetMapping(path = "/{id}")
    public Player getById(@PathVariable String id) {
        return service.getReferenceById(Long.valueOf(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        service.deleteById(Long.valueOf(id));
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Player>> getPlayers() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Player> newPlayer(@RequestBody PlayerForm playerForm, UriComponentsBuilder uriBuilder) {
        Player player = new Player();
        player.setCodeName(playerForm.getCodeName());
        player = service.save(player);
        return ResponseEntity.created(
                uriBuilder.path("/player/{id}").buildAndExpand(player.getId()).toUri()).build();
    }

}

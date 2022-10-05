package net.starkenberg.training.lasertagapi.team;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Comparator;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/teams")
public class TeamController {
    private final TeamRepository repo;

    public TeamController(TeamRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<List<Team>> getTeams() {
        List<Team> teams = repo.findAll();
        teams.sort(Comparator.comparing(Team::getId));
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable String id) {
        return new ResponseEntity<>(repo.getReferenceById(Long.valueOf(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Team> newTeam(@RequestBody Team team, UriComponentsBuilder uriBuilder) {
        team = repo.save(team);
        return ResponseEntity.created(
                uriBuilder.path("/teams/{id}").buildAndExpand(team.getId()).toUri()).build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable String id, @RequestBody Team team) {
        if (team.getId() == Long.valueOf(id)) {
            repo.save(team);
            return new ResponseEntity<>(team, HttpStatus.OK);
        } else {
            throw new RuntimeException("IDs do not match");
        }
    }
}

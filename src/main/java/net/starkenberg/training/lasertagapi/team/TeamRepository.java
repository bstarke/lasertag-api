package net.starkenberg.training.lasertagapi.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    @Override
    @Query(value = "SELECT DISTINCT t FROM Team t LEFT JOIN FETCH t.players")
    List<Team> findAll();
}

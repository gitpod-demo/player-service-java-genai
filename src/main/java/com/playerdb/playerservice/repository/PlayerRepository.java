package com.playerdb.playerservice.repository;
import com.playerdb.playerservice.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, String> {

}

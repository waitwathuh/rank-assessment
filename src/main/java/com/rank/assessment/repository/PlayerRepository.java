package com.rank.assessment.repository;

import com.rank.assessment.entity.Player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlayerRepository extends JpaRepository<Player, Integer> {
	
	@Query("SELECT p FROM Player p WHERE p.id = :id")
    Player findByPlayerId(@Param("id") int id);
	
	@Query("SELECT p FROM Player p WHERE p.username = :username")
    Player findByPlayerUsername(@Param("username") String username);

}

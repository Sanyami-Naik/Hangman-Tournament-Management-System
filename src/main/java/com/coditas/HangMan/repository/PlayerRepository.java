package com.coditas.HangMan.repository;

import com.coditas.HangMan.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player,Long> {
}

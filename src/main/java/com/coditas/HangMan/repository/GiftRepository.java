package com.coditas.HangMan.repository;

import com.coditas.HangMan.entity.Gift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftRepository extends JpaRepository<Gift,Long> {
}

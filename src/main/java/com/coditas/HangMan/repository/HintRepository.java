package com.coditas.HangMan.repository;

import com.coditas.HangMan.entity.Hint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HintRepository extends JpaRepository<Hint,Long> {
    Optional<Hint> findByHintName( String hintName);
}

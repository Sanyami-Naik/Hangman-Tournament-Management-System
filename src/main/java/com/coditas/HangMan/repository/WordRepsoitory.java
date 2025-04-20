package com.coditas.HangMan.repository;

import com.coditas.HangMan.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepsoitory extends JpaRepository<Word,Long> {
}

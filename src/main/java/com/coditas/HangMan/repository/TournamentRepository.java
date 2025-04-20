package com.coditas.HangMan.repository;

import com.coditas.HangMan.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface TournamentRepository extends JpaRepository<Tournament,Long> {

//    List<Tournament> findByCategoryList_CategoryNameIn(List<String> categoryNames, Pageable pageable);
}

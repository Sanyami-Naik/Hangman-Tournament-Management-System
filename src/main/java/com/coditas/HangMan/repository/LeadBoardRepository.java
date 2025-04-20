package com.coditas.HangMan.repository;

import com.coditas.HangMan.entity.LeadBoard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface LeadBoardRepository extends JpaRepository<LeadBoard,Long> {

    List<LeadBoard> findByPlayTimeBetweenAndTournament_CategoryList_CategoryNameIn
            (Long playTimeStart, Long playTimeEnd,
             Collection<String> categoryNames,
             Pageable pageable);

    List<LeadBoard> findByPlayTimeBetween(Long playTimeStart, Long playTimeEnd, Pageable pageable);

}

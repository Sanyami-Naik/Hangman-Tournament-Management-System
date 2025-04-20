package com.coditas.HangMan.service.interfaces;


import com.coditas.HangMan.payload.WordDto;

import java.util.List;


    public interface GameService {
        List<WordDto> getAllQuestions(long TournamentId);

}

package com.coditas.HangMan.service.impl;
import com.coditas.HangMan.entity.Hint;
import com.coditas.HangMan.entity.Tournament;
import com.coditas.HangMan.entity.Word;
import com.coditas.HangMan.payload.WordDto;
import com.coditas.HangMan.repository.TournamentRepository;
import com.coditas.HangMan.repository.WordRepsoitory;
import com.coditas.HangMan.service.interfaces.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class GameServiceImpl implements GameService {


    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private WordRepsoitory wordRepsoitory;

    @Override
    public List<WordDto> getAllQuestions(long tournamentId) {
        Tournament tournament=tournamentRepository.findById(tournamentId).get();
        List<Word> wordList=tournament.getWordList();
        List<WordDto> wordDtoList=new ArrayList<>();
        for (Word w : wordList) {
            WordDto wordDto=new WordDto();
            wordDto.setWordName(w.getWordName());
            List<Hint> hintList=w.getHintList();
            int num= ThreadLocalRandom.current().nextInt(0,hintList.size());
            Hint hint=hintList.get(num);
            wordDto.setHintName(hint.getHintName());
            System.out.println(wordDto);
            System.out.println(wordDtoList);
            wordDtoList.add(wordDto);

        }

        return wordDtoList;
    }
}

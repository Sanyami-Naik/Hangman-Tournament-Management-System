package com.coditas.HangMan.controller;

import com.coditas.HangMan.payload.WordDto;
import com.coditas.HangMan.service.interfaces.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GameController {
    @Autowired
    private GameService gameService;

    @GetMapping("/questionSet/{tournamentId}")
    public ResponseEntity<List<WordDto>> getAllQuestionSet(@PathVariable long tournamentId){
        return new ResponseEntity<>(gameService.getAllQuestions(tournamentId), HttpStatus.OK);
    }
}

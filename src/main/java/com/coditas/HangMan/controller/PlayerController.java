package com.coditas.HangMan.controller;


import com.coditas.HangMan.entity.Tournament;
import com.coditas.HangMan.payload.*;
import com.coditas.HangMan.service.interfaces.LeadBoardService;
import com.coditas.HangMan.service.interfaces.PlayerService;
import com.coditas.HangMan.service.interfaces.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/player")
@CrossOrigin

public class PlayerController {
    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private PlayerService playerService;


    @Autowired
    LeadBoardService leadBoardService;

    @GetMapping("/availableTournaments/{playerId}")
    public ResponseEntity<List<TournamentDto>> getAllTournament(@PathVariable long playerId){
        return new ResponseEntity<>(tournamentService.getAllTournament(playerId), HttpStatus.OK);
    }
//
//    @GetMapping("/getTournamentQuestionSet/{tournamentId}")
//    public List<WordDto> getTournamentQuestionSet(@PathVariable long tournamentId)
//    {
//        return tournamentService.getTournamentQuestionSet(tournamentId);
//    }

    @GetMapping("/selfCreated/{playerId}")
    public ResponseEntity<List<TournamentDto>> getAllPlayerCreatedTournament(@PathVariable long playerId){
        return new ResponseEntity<>(tournamentService.getAllPlayerCreatedTournament(playerId), HttpStatus.OK);
    }

    @GetMapping("/getSelfInfo/{playerId}")
    public PlayerDto getSelfInfo(@PathVariable long playerId){
        return playerService.getPersonalInfo(playerId);
    }

    @PostMapping("/createTournament/{playerId}")
    public TournamentDto createTournament(@PathVariable long playerId,@RequestBody TournamentDto tournamentDto)
    {
        return tournamentService.createTournament(tournamentDto,playerId);

    }


    @PutMapping("/delete/{tournamentId}")
    public ResponseEntity<HttpStatus> deleteTournament(@PathVariable long tournamentId){
        tournamentService.deleteTournament(tournamentId);
        return  new ResponseEntity<>( HttpStatus.CREATED);
    }

    @GetMapping("/graph/{playerId}")
    public ResponseEntity<GraphDto> getProgressGraph(@PathVariable long playerId){
        return new ResponseEntity<>(playerService.playerProgress(playerId), HttpStatus.OK);
    }


    @GetMapping("/getLeadBoardForParticularPlayer/{playerId}")
    public List<LeadBoardDto> getLeadBoardForParticularPlayer(@PathVariable long playerId)
    {
        return leadBoardService.getLeadBoardForParticularPlayer(playerId);
    }


    @PostMapping("/stopTournament")
    public ResponseEntity<TournamentDto> stopTournament(@RequestBody StopDto stopDto){
        return new ResponseEntity<>(tournamentService.stopTournament(stopDto), HttpStatus.OK);
    }


    @PostMapping("/logout")
    public ResponseEntity<LogoutDto> getProgressGraph(@RequestBody LogoutDto logoutDto){
        return new ResponseEntity<>(playerService.playerLogout(logoutDto), HttpStatus.OK);
    }
}

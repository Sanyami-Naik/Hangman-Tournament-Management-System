package com.coditas.HangMan.controller;

import com.coditas.HangMan.payload.LeadBoardDto;
import com.coditas.HangMan.service.interfaces.LeadBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LeadBoardController {
    @Autowired
    private LeadBoardService leadBoardService;

    @PostMapping("/addLeadBoard")
    public ResponseEntity<LeadBoardDto> addToLeadBoard(@RequestBody LeadBoardDto leadBoardDto){
        return  new ResponseEntity<>(leadBoardService.addResult(leadBoardDto), HttpStatus.CREATED);

    }

    @GetMapping("/getScoresForTournament/{tournamentId}")
    public ResponseEntity< List<LeadBoardDto>> getLeadBoard(@PathVariable long tournamentId){
        return  new ResponseEntity<>(leadBoardService.getLeadBoard(tournamentId), HttpStatus.CREATED);

    }

    @PutMapping("/delete/{tournamentId}")
    public ResponseEntity<HttpStatus> deleteTournament(@RequestBody LeadBoardDto leadBoardDto){
        return  new ResponseEntity<>( HttpStatus.CREATED);
    }


    @GetMapping("/getAllLeaderBoardList")
    public List<LeadBoardDto> getAllLeaderBoardRecords(
            @RequestParam(value = "categoryValues",
                    defaultValue = "default",
                    required = false) String categoriesStr,
            @RequestParam(value = "duration",defaultValue = "12",required = false) int duration,
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "leadBoardId",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir

    ) {
        if(categoriesStr.equals("default")) {
            return leadBoardService.getLeaderBoardLists(duration,pageNo,pageSize,sortBy,sortDir);
        }
        List<String> categories = Arrays.asList(categoriesStr.split(","));
        return leadBoardService.getLeaderBoardList(categories,duration,pageNo,pageSize,sortBy,sortDir);
    }
}

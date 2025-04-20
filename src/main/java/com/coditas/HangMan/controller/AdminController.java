package com.coditas.HangMan.controller;
import com.coditas.HangMan.entity.Category;
import com.coditas.HangMan.entity.Player;
import com.coditas.HangMan.entity.RedeemRequest;
import com.coditas.HangMan.entity.Tournament;
import com.coditas.HangMan.payload.CategoryDto;
import com.coditas.HangMan.payload.HandlingRequestDto;
import com.coditas.HangMan.payload.PlayerDto;
import com.coditas.HangMan.payload.TournamentDto;
import com.coditas.HangMan.repository.CategoryRepository;
import com.coditas.HangMan.repository.PlayerRepository;
import com.coditas.HangMan.repository.RedeemRequestRepository;
import com.coditas.HangMan.repository.TournamentRepository;
import com.coditas.HangMan.service.interfaces.CategoryService;
import com.coditas.HangMan.service.interfaces.PlayerService;
import com.coditas.HangMan.service.interfaces.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@CrossOrigin
public class AdminController {

    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    private PlayerService playerService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    private TournamentService tournamentService;


    @Autowired
    private RedeemRequestRepository redeemRequestRepository;

    @GetMapping("/getPlayerList")
    public List<Player> playerList()
    {
        return playerRepository.findAll();
    }


    @GetMapping("/getCategoryList")
    public List<CategoryDto> categoryList()
    {
        return categoryService.showCategories();
    }

    @PostMapping("/addCategory")
    public ResponseEntity<HashMap<CategoryDto,String>> addCategory(@RequestBody CategoryDto categoryDto)
    {
        return categoryService.addCategory(categoryDto);
    }

    @DeleteMapping("/deleteCategory/{categoryId}")
    public CategoryDto deleteCategory(@PathVariable long categoryId)
    {
        return categoryService.deleteCategory(categoryId);
    }

    @GetMapping("/getPlayers")
    public ResponseEntity<List<PlayerDto>> getAllPlayer(){
        return new ResponseEntity<>(playerService.getAllPlayers(), HttpStatus.OK);
    }




    @GetMapping("/getPendingTournamentRequests")
    public List<TournamentDto> getPendingTournamentRequests()
    {
        return tournamentService.getPendingTournamentRequests();
    }

    @PostMapping("/approveTournamentRequest")
    public  ResponseEntity <HttpStatus> approveTournamentRequest(@RequestBody HandlingRequestDto handlingRequestDto)
    {
        Tournament tournament =tournamentService.approveTournamentRequest( handlingRequestDto);
        System.out.println(tournament.getTournamentStatus());
        return new ResponseEntity<>(HttpStatus.OK) ;

    }


    @GetMapping("/getAllPlayers")
    public List<PlayerDto> getAllPlayers()
    {
        return playerService.getAllPlayers();
    }











}

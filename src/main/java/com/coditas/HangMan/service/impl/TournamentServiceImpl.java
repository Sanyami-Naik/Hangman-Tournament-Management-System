package com.coditas.HangMan.service.impl;

import com.coditas.HangMan.entity.*;
import com.coditas.HangMan.payload.HandlingRequestDto;
import com.coditas.HangMan.payload.StopDto;
import com.coditas.HangMan.payload.TournamentDto;
import com.coditas.HangMan.payload.WordDto;
import com.coditas.HangMan.repository.*;
import com.coditas.HangMan.service.interfaces.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class TournamentServiceImpl implements TournamentService {
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private WordRepsoitory wordRepsoitory;
    @Autowired
    private HintRepository hintRepository;

    @Autowired
    private PlayerRepository playerRepository;


    @Autowired
    private LeadBoardRepository leadBoardRepository;


    @Autowired
    private CategoryRepository categoryRepository;

    public TournamentDto createTournament(TournamentDto tournamentDto,long playerId) {
        List<Category> list=tournamentDto.getCategoryList().stream().map(s->categoryRepository.findByCategoryName(s)).collect(Collectors.toList());
        Tournament tournament=tournamentRepository.save(mapToTournamentEntity(tournamentDto));
        tournament.setPlayer(playerRepository.findById(playerId).get());
        tournament.setCategoryList(list);
        List<WordDto> wordDtoList=tournamentDto.getWordDtoList();
        for (WordDto w :wordDtoList) {
            Word word=new Word();
            word.setWordName(w.getWordName());
            word.setTournament(tournament);
            Word word1=wordRepsoitory.save(word);
            Hint hint=new Hint();
            hint.setHintName(w.getHintName());
            hint.setWord(word1);
            hintRepository.save(hint);
        }

        return mapToTournamentDto(tournament);
    }

    @Override
    public List<TournamentDto> getAllTournament(long playerId) {
        List<Tournament> tournaments=tournamentRepository.findAll();
        List<Tournament> tournamentList=tournaments.stream().filter(s->s.getPlayer()!=null && s.getPlayer().getPlayerId()!=playerId && !s.isDeleted() && s.getTournamentStatus().equals("APPROVED")).collect(Collectors.toList());

        return tournamentList.stream().map(t->mapToTournamentDto(t)).collect(Collectors.toList());

    }

    @Override
    public List<TournamentDto> getAllPlayerCreatedTournament(long playerId) {
        List<Tournament> tournaments=tournamentRepository.findAll();
        List<Tournament> tournamentList=tournaments.stream().filter( s->s.getPlayer()!=null &&s.getPlayer().getPlayerId()==playerId  && s.getTournamentStatus().equals("APPROVED") && !s.isDeleted()).collect(Collectors.toList());

        return tournamentList.stream().map(t->mapToTournamentDto(t)).collect(Collectors.toList());
    }




    public List<TournamentDto> getPendingTournamentRequests()
    {
        List<Tournament> list=tournamentRepository.findAll().stream().filter(s->s.getTournamentStatus().equals("PENDING") && !s.isDeleted()).collect(Collectors.toList());
        return list.stream().map(t->mapToTournamentDto(t)).collect(Collectors.toList());
    }

    public Tournament approveTournamentRequest(HandlingRequestDto handlingRequestDto)
    {
        Tournament tournament = tournamentRepository.findById(handlingRequestDto.getTournamentId()).get();
        tournament.setTournamentStatus(handlingRequestDto.getStatus().toUpperCase().trim());
        tournamentRepository.save(tournament);
        return tournament;

    }

    @Override
    public void deleteTournament(long tournamentId) {
        Tournament tournament=tournamentRepository.findById(tournamentId).get();
        tournament.setDeleted(true);
        tournament.setCategoryList(null);
        tournament.setLeadBoardList(null);
        tournament.setWordList(null);
        tournament.setPlayer(null);
        tournamentRepository.save(tournament);
    }


    @Override
    public TournamentDto stopTournament(StopDto stopDto) {
        Tournament tournament=tournamentRepository.findById(stopDto.getTournamentId()).get();
        Player player=playerRepository.findById(stopDto.getPlayerId()).get();
        tournament.setTournamentStatus("STOPPED");
        List<LeadBoard> leadBoards=tournament.getLeadBoardList();
        List<LeadBoard> sortedList=leadBoards.stream().sorted(Comparator.comparing(LeadBoard::getScore).reversed().thenComparing(LeadBoard::getGameDuration)).collect(Collectors.toList());

        try {
            LeadBoard leadBoard = sortedList.get(0);
            Player player1 = leadBoard.getPlayer();
            player1.setTotalCoins(player1.getTotalCoins() + tournament.getFirstPriceCoins());

            LeadBoard leadBoard1 = sortedList.get(1);
            Player player2 = leadBoard1.getPlayer();
            player2.setTotalCoins(player2.getTotalCoins() + tournament.getSecondPriceCoins());

            LeadBoard leadBoard2 = sortedList.get(2);
            Player player3 = leadBoard2.getPlayer();
            player3.setTotalCoins(player3.getTotalCoins() + tournament.getThirdPriceCoins());

            playerRepository.save(player1);
            playerRepository.save(player2);
            playerRepository.save(player3);
        }
        catch (Exception e)
        {

        }

        Tournament tournament1= tournamentRepository.save(tournament);

        return mapToTournamentDto(tournament1);
    }

//    @Override
//    public List<TournamentDto> getAllTournaments(List<String> categories,int pageNo,int pageSize,String sortBy,String sortDir) {
//
//        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
//                : Sort.by(sortBy).descending();
//        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
//
//        List<Tournament> tournaments =
//                tournamentRepository.findByCategoryList_CategoryNameIn(categories, (java.awt.print.Pageable) pageable);
//
//        return tournaments.stream().map(this::mapToTournamentDto).collect(Collectors.toList());
//    }

//    @Override
//    public List<TournamentDto> getAllTournamentss(int pageNo,int pageSize,String sortBy,String sortDir) {
//
//        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
//                : Sort.by(sortBy).descending();
//        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
//
//        Page<Tournament> tournaments =
//                tournamentRepository.findAll(pageable);
//
//        List<Tournament> tournamentList = tournaments.getContent();
//
//        return tournamentList.stream().map(this::mapToTournamentDto).collect(Collectors.toList());
//    }


    public TournamentDto mapToTournamentDto (Tournament tournament){
        TournamentDto tournamentDto=new TournamentDto();
        tournamentDto.setTournamentId(tournament.getTournamentId());
        tournamentDto.setTournamentName(tournament.getTournamentName());
        tournamentDto.setTournamentStatus(tournament.getTournamentStatus());
        tournamentDto.setFirstPriceCoins(tournament.getFirstPriceCoins());
        tournamentDto.setSecondPriceCoins(tournament.getSecondPriceCoins());
        tournamentDto.setThirdPriceCoins(tournament.getThirdPriceCoins());
        tournamentDto.setCategoryList(tournament.getCategoryList().stream().map(s->s.getCategoryName()).collect(Collectors.toList()));
//        tournamentDto.setWordList(tournament.getWordList());
        return tournamentDto;
    }

    public Tournament mapToTournamentEntity(TournamentDto tournamentDto){
        Tournament tournament=new Tournament();
        tournament.setTournamentName(tournamentDto.getTournamentName());
        tournament.setFirstPriceCoins(tournamentDto.getFirstPriceCoins());
        tournament.setSecondPriceCoins(tournamentDto.getSecondPriceCoins());
        tournament.setThirdPriceCoins(tournamentDto.getThirdPriceCoins());
       return tournament;
    }
}

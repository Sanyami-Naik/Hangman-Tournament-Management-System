package com.coditas.HangMan.service.impl;
import com.coditas.HangMan.entity.Category;
import com.coditas.HangMan.entity.LeadBoard;
import com.coditas.HangMan.entity.Player;
import com.coditas.HangMan.entity.Tournament;
import com.coditas.HangMan.payload.LeadBoardDto;
import com.coditas.HangMan.repository.CategoryRepository;
import com.coditas.HangMan.repository.LeadBoardRepository;
import com.coditas.HangMan.repository.PlayerRepository;
import com.coditas.HangMan.repository.TournamentRepository;
import com.coditas.HangMan.service.interfaces.LeadBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeadBoardServiceImpl implements LeadBoardService {

    @Autowired
    private LeadBoardRepository leadBoardRepository;
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    public LeadBoardDto addResult(LeadBoardDto leadBoardDto) {
        LeadBoard leadBoard = leadBoardRepository.save(mapToLeadBoard(leadBoardDto));
        Player player = leadBoard.getPlayer();
        player.setGamesPlayed(player.getGamesPlayed() + 1);
        playerRepository.save(player);
        return mapToLeadBoardDto(leadBoard);
    }

    @Override
    public List<LeadBoardDto> getLeadBoard(long tournamentId) {
        Tournament tournament = tournamentRepository.findById(tournamentId).get();
        List<LeadBoard> leadBoards = tournament.getLeadBoardList();
        List<LeadBoardDto> leadBoardDtos = leadBoards.stream().map(s -> mapToLeadBoardDto(s)).collect(Collectors.toList());
        return leadBoardDtos;
    }

    @Override
    public List<LeadBoardDto> getLeadBoardWithId(long tournamentId) {
        List<LeadBoard> leadBoardList = leadBoardRepository.findAll();
        List<LeadBoard> specificList = leadBoardList.stream().filter(s -> s.getTournament().getTournamentId() == tournamentId).sorted(Comparator.comparing(LeadBoard::getScore).reversed().thenComparing(LeadBoard::getGameDuration)).collect(Collectors.toList());
        List<LeadBoardDto> leadBoardDtos = specificList.stream().map(s -> mapToLeadBoardDto(s)).collect(Collectors.toList());
        return leadBoardDtos;
    }


    public List<LeadBoardDto> getLeadBoardForParticularPlayer(long playerId) {
        return leadBoardRepository.findAll().stream().filter(l -> l.getPlayer().getPlayerId() == playerId).map(l -> mapToLeadBoardDto(l)).collect(Collectors.toList());
    }

    public LeadBoardDto mapToLeadBoardDto(LeadBoard leadBoard) {
        LeadBoardDto leadBoardDto = new LeadBoardDto();
        leadBoardDto.setScore(leadBoard.getScore());
        leadBoardDto.setGameDuration(leadBoard.getGameDuration());
        leadBoardDto.setPlayerId(leadBoard.getPlayer().getPlayerId());
        leadBoardDto.setTournamentId(leadBoard.getTournament().getTournamentId());
        leadBoardDto.setPlayerName(leadBoard.getPlayer().getPlayerName());
        leadBoardDto.setTournamentName(leadBoard.getTournament().getTournamentName());
        leadBoardDto.setPlayTime(leadBoard.getPlayTime());

        return leadBoardDto;
    }

    public LeadBoard mapToLeadBoard(LeadBoardDto leadBoardDto) {
        LeadBoard leadBoard = new LeadBoard();
        leadBoard.setScore(leadBoardDto.getScore());
        leadBoard.setGameDuration(leadBoardDto.getGameDuration());
        Player player = playerRepository.findById(leadBoardDto.getPlayerId()).get();
        Tournament tournament = tournamentRepository.findById(leadBoardDto.getTournamentId()).get();
        leadBoard.setPlayer(player);
        leadBoard.setTournament(tournament);
        leadBoard.setPlayTime(leadBoardDto.getPlayTime());
        return leadBoard;

    }

    @Override
    public List<LeadBoardDto> getLeaderBoardList(List<String> categories, int durationMonths, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Date date = new Date();
        Long endDate = date.getTime();

        System.out.println(endDate);

        Long startTime = endDate - (2628002880L * durationMonths);

        System.out.println(startTime);

        Page<LeadBoard> leadBoardList1 = leadBoardRepository.findAll(pageable);

        List<LeadBoard> leadBoardList = leadBoardList1.getContent();
        System.out.println(leadBoardList);

        List<Category> categoryList= new ArrayList<>();

        for(String s : categories) {
            Category c  = categoryRepository.findByCategoryName(s);
            categoryList.add(c);

        }
        System.out.println(categoryList
        );
//        ************************************************************************************
        List<Tournament> tournaments = new ArrayList<>();
        for (Category c : categoryList) {
            List<Tournament> tournaments1 = c.getTournamentList();
            tournaments.addAll(tournaments1);
        }
        System.out.println(tournaments);

        List<LeadBoard> l2 = new ArrayList<>();
        for (LeadBoard l :leadBoardList) {
            for (Tournament t : tournaments) {
                if(l.getTournament().equals(t)) {
                    l2.add(l);
                }
            }
        }
        System.out.println(l2);
//        ************************************************************************************

//        List<LeadBoard> list = leadBoardList.stream().filter(i->i.getPlayTime()>=startTime &&
//                i.getPlayTime()<=endDate).collect(Collectors.toList());
//
//        List<LeadBoard> newList = new ArrayList<>();
//
//        System.out.println(list);
//        System.out.println(categoryList);

//        for (Category c : categoryList) {t
//            for(LeadBoard l : list) {
//                if(l.getTournament().getCategoryList().contains(c)) {
//                   newList.add(l);
//                }
//            }
//            list.stream().filter(i->i.getTournament()
//                    .getCategoryList()
//                    .stream()
//                    .filter(j->j.getCategoryId()==c.getCategoryId()).collect(Collectors.toList()));
//        }


//          i.getTournament().getCategoryList().containsAll(categoryList))

        return l2.stream().map(this::mapToLeadBoardDto).collect(Collectors.toList());
    }

    @Override
    public List<LeadBoardDto> getLeaderBoardLists(int durationMonths, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Date date = new Date();
        Long endDate = date.getTime();
        System.out.println(endDate);

        Long startTime = endDate - (2628002880L * durationMonths);
        System.out.println(startTime);

        Page<LeadBoard> leadBoardList1 = leadBoardRepository.findAll(pageable);

        List<LeadBoard> leadBoardList = leadBoardList1.getContent();



        List<LeadBoard> list = leadBoardList.stream().filter(i->i.getPlayTime()>=startTime && i.getPlayTime()<=endDate).collect(Collectors.toList());

        return list.stream().map(this::mapToLeadBoardDto).collect(Collectors.toList());

    }


}
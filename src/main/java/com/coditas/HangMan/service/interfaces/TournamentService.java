package com.coditas.HangMan.service.interfaces;

import com.coditas.HangMan.entity.Tournament;
import com.coditas.HangMan.payload.HandlingRequestDto;
import com.coditas.HangMan.payload.StopDto;
import com.coditas.HangMan.payload.TournamentDto;
import com.coditas.HangMan.payload.WordDto;

import java.util.List;

public interface TournamentService {
//    public List<TournamentDto> getAllTournamentss(int pageNo,int pageSize,String sortBy,String sortDir) ;
//
//    public List<TournamentDto> getAllTournaments(List<String> categories,int pageNo,int pageSize,String sortBy,String sortDir);

    TournamentDto createTournament(TournamentDto tournamentDto,long playerId);
    List<TournamentDto> getAllTournament(long playerId);
    List<TournamentDto> getAllPlayerCreatedTournament(long playerId);

     List<TournamentDto> getPendingTournamentRequests();
     Tournament approveTournamentRequest(HandlingRequestDto handlingRequestDto);

     void deleteTournament(long tournamentId);


    TournamentDto stopTournament(StopDto stopDto);
}

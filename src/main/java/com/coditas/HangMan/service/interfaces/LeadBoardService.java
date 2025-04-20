package com.coditas.HangMan.service.interfaces;

import com.coditas.HangMan.payload.LeadBoardDto;

import java.util.List;

public interface LeadBoardService {

    LeadBoardDto addResult(LeadBoardDto leadBoardDto);
    List<LeadBoardDto>  getLeadBoard(long tournamentId);

    public List<LeadBoardDto> getLeadBoardForParticularPlayer(long playerId);

    List<LeadBoardDto> getLeadBoardWithId(long tournamentId);

    List<LeadBoardDto> getLeaderBoardList(List<String> categories, int durationMonths, int pageNo, int pageSize, String sortBy, String sortDir);

    List<LeadBoardDto> getLeaderBoardLists(int durationMonths, int pageNo, int pageSize, String sortBy, String sortDir);


}

package com.coditas.HangMan.payload;


import lombok.Data;

@Data

public class LeadBoardDto {
    private float score;
    private long gameDuration;
    private String tournamentName;
    private String playerName;
    private long tournamentId;
    private long playerId;
    private Long playTime;
}

package com.coditas.HangMan.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDto {
    private  long playerId;
    private String playerName;
    private long lastLoginTime;
    private int gamesPlayed;
    private int totalCoins;
    private  String userName;
    private String userPassword;
    private long userId;
    private long NoOfTournamentsCreated;
//    private long redeemRequestId;
//    private long leadBoardId;
}

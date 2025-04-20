package com.coditas.HangMan.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Table(name="user",uniqueConstraints = {
//        @UniqueConstraint(columnNames = {"userName"}),
//})
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long playerId;
    private String playerName;
    private long  lastLoginTime;
    private int gamesPlayed;
    private boolean isDeleted=false;
    private int totalCoins;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "player",cascade = CascadeType.ALL)
    private List<RedeemRequest> redeemRequestList;

    @OneToMany(mappedBy = "player",cascade = CascadeType.ALL)
    private List<LeadBoard> leadBoardList;

    @OneToMany(mappedBy = "player")
    private List<Tournament> tournaments;



}

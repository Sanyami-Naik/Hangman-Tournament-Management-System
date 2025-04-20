package com.coditas.HangMan.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tournament {
    @Id
    @GeneratedValue()
    private long tournamentId;
    private  String tournamentName;
    private int firstPriceCoins;
    private int secondPriceCoins;
    private int thirdPriceCoins;
    private boolean isDeleted=false;
    private String tournamentStatus="PENDING";



    @ManyToMany
    private List<Category> categoryList;

//    @ManyToMany
//    private List<Player> playerList;

    @OneToMany(mappedBy = "tournament",cascade = CascadeType.ALL)
    private List<LeadBoard> leadBoardList;

    @ManyToOne
    private Player player;

    @OneToMany(mappedBy = "tournament",cascade = CascadeType.ALL)
    private List<Word> wordList;


}

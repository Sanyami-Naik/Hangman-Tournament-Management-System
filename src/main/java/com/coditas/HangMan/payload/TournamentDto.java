package com.coditas.HangMan.payload;

import com.coditas.HangMan.entity.Category;
import com.coditas.HangMan.entity.LeadBoard;
import com.coditas.HangMan.entity.Word;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TournamentDto {

    private long tournamentId;
    private  String tournamentName;
    private int firstPriceCoins;
    private int secondPriceCoins;
    private int thirdPriceCoins;

    private String tournamentStatus;

    private List<String> categoryList;
    private List<WordDto> wordDtoList;
}

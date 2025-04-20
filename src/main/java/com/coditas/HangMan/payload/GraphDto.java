package com.coditas.HangMan.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GraphDto {
    List<String> tournamentName;
    List<Long> gameDuration;

}

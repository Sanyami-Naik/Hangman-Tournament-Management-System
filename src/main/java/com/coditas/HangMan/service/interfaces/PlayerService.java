package com.coditas.HangMan.service.interfaces;
import com.coditas.HangMan.payload.GraphDto;
import com.coditas.HangMan.payload.LogoutDto;
import com.coditas.HangMan.payload.PlayerDto;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface PlayerService {
    List<PlayerDto> getAllPlayers();
    PlayerDto createPlayer(PlayerDto  playerDto);
    PlayerDto getPersonalInfo( long playerId);

    GraphDto playerProgress(long playerId);
    LogoutDto playerLogout(LogoutDto logoutDto);

}

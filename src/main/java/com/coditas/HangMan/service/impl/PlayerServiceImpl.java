package com.coditas.HangMan.service.impl;

import com.coditas.HangMan.entity.LeadBoard;
import com.coditas.HangMan.entity.Player;
import com.coditas.HangMan.entity.Role;
import com.coditas.HangMan.entity.User;
import com.coditas.HangMan.payload.GraphDto;
import com.coditas.HangMan.payload.LogoutDto;
import com.coditas.HangMan.payload.PlayerDto;
import com.coditas.HangMan.repository.*;
import com.coditas.HangMan.service.interfaces.PlayerService;
import com.coditas.HangMan.utils.EmailSenderService;
import com.coditas.HangMan.utils.PasswordEncoderProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private LeadBoardRepository leadBoardRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private  EmailSenderService emailSenderService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TournamentRepository tournamentRepository;
    @Override
    public List<PlayerDto> getAllPlayers() {
        List<Player> playerList=playerRepository.findAll();
        List<PlayerDto> playerDtoList = new LinkedList<>();
        if(playerList.size()>0) {
            playerDtoList = playerList.stream().map(this::mapToPlayerDto).collect(Collectors.toList());
        }
        return playerDtoList;
    }

    @Override
    public PlayerDto createPlayer(PlayerDto playerDto) {

        Player player=playerRepository.save(mapToPlayerEntity(playerDto));
        emailSenderService.sendSimpleEmail(player.getUser().getUserName(),"Your account has been created successfully","Sign Up");
        return mapToPlayerDto(player);
    }

    public PlayerDto mapToPlayerDto(Player player){

        PlayerDto playerDto=new PlayerDto();
        playerDto.setPlayerId(player.getPlayerId());
        playerDto.setPlayerName(player.getPlayerName());
        playerDto.setLastLoginTime(player.getLastLoginTime());
        playerDto.setTotalCoins(player.getTotalCoins());
        playerDto.setGamesPlayed(player.getGamesPlayed());
        playerDto.setUserId(player.getUser().getUserId());
        playerDto.setUserName(player.getUser().getUserName());
        playerDto.setUserPassword(player.getUser().getUserPassword());
        return playerDto;

    }


    public Player mapToPlayerEntity(PlayerDto playerDto){

        Player player=new Player();
        player.setPlayerName(playerDto.getPlayerName());
        player.setTotalCoins(playerDto.getTotalCoins());
        player.setGamesPlayed(playerDto.getGamesPlayed());
        User user=new User();
        user.setUserPassword(PasswordEncoderProvider.passwordEncoder(playerDto.getUserPassword()));
        user.setUserName(playerDto.getUserName());
        Role role=roleRepository.findByRoleName("ROLE_PLAYER").get();
        user.setRoleSet(Collections.singleton(role));
        User user1=userRepository.save(user);
        player.setUser(user1);

//        player.setLastLoginTime(Time.valueOf(LocalTime.now()));
//        User user=userRepository.findById(playerDto.getUserId()).get();
//        player.setUser(user);
        return player;

    }

    public PlayerDto getPersonalInfo(@PathVariable long playerId)
    {
        PlayerDto playerDto=mapToPlayerDto(playerRepository.findById(playerId).get());
        playerDto.setNoOfTournamentsCreated(tournamentRepository.findAll().stream().filter(t->t.getPlayer()!=null && t.getPlayer().getPlayerId()== playerDto.getPlayerId() && t.getTournamentStatus().equals("APPROVED")  ).count());

        return playerDto;
    }

    @Override
    public GraphDto playerProgress(long playerId) {
        Player player=playerRepository.findById(playerId).get();
        List<LeadBoard> leadBoardList=player.getLeadBoardList();
        List<Long> timeTaken=leadBoardList.stream().map(s->s.getGameDuration()).collect(Collectors.toList());
        System.out.println(timeTaken);
        List<String> tournamentName=leadBoardList.stream().map(s->s.getTournament().getTournamentName()).collect(Collectors.toList());
        System.out.println(tournamentName);
        GraphDto graphDto=new GraphDto();
        graphDto.setGameDuration(timeTaken);
        graphDto.setTournamentName(tournamentName);
        System.out.println(graphDto);
        return graphDto;
    }


    @Override
    public LogoutDto playerLogout(LogoutDto logoutDto) {
        System.out.println(logoutDto.getPlayerId());
        System.out.println(logoutDto.getDuration());
        Player player=playerRepository.findById(logoutDto.getPlayerId()).get();
        player.setLastLoginTime(logoutDto.getDuration());
        Player player1=playerRepository.save(player);
        System.out.println(player1);
        return logoutDto;
    }
}

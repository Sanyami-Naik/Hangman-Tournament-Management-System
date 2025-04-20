package com.coditas.HangMan.service.impl;


import com.coditas.HangMan.entity.Gift;
import com.coditas.HangMan.entity.Player;
import com.coditas.HangMan.entity.RedeemRequest;
import com.coditas.HangMan.payload.AddRequestDto;
import com.coditas.HangMan.payload.GiftRequestHandlingDto;
import com.coditas.HangMan.payload.RedeemRequestDto;
import com.coditas.HangMan.repository.GiftRepository;
import com.coditas.HangMan.repository.PlayerRepository;
import com.coditas.HangMan.repository.RedeemRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RedeemRequestImpl {

    @Autowired
    private RedeemRequestRepository repository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GiftRepository giftRepository;

    public RedeemRequest addRequest(AddRequestDto addRequestDto){
        Player player=playerRepository.findById(addRequestDto.getPlayerId()).get();
        Gift gift=giftRepository.findById(addRequestDto.getGiftId()).get();
        RedeemRequest redeemRequest=new RedeemRequest();
        redeemRequest.setGift(gift);
        redeemRequest.setPlayer(player);
        return repository.save(redeemRequest);
    }

    public String updateStatus(GiftRequestHandlingDto giftRequestHandlingDto){
        RedeemRequest redeemRequest = repository.findById(giftRequestHandlingDto.getRedeemRequestId()).get();
        Player player=redeemRequest.getPlayer();
        Gift gift=redeemRequest.getGift();
        if(giftRequestHandlingDto.getStatus().equals("APPROVED")&& (player.getTotalCoins()-gift.getGiftValue())>=0){
            redeemRequest.setRedeemRequestStatus("APPROVED");
            player.setTotalCoins(player.getTotalCoins()-gift.getGiftValue());
            playerRepository.save(player);
            repository.save(redeemRequest);
            return "approved";
        }else {
            redeemRequest.setRedeemRequestStatus("REJECTED");
            repository.save(redeemRequest);
            return "rejected";
        }

    }

    public String declineStatus(GiftRequestHandlingDto giftRequestHandlingDto) {

        RedeemRequest redeemRequest=repository.findById(giftRequestHandlingDto.getRedeemRequestId()).get();
        redeemRequest.setRedeemRequestStatus("DECLINED");
        repository.save(redeemRequest);
        return "Declined";
    }

    public List<RedeemRequestDto> getPendingGiftRequests()
    {
        return repository.findAll().stream().filter(r->r.getRedeemRequestStatus().equals("PENDING")).map(r->mapToDto(r)).collect(Collectors.toList());
    }


    public RedeemRequestDto mapToDto(RedeemRequest redeemRequest)
    {
        RedeemRequestDto redeemRequestDto=new RedeemRequestDto();
        redeemRequestDto.setRedeemRequestId(redeemRequest.getRedeemRequestId());
        redeemRequestDto.setRedeemRequestStatus(redeemRequest.getRedeemRequestStatus());

        redeemRequestDto.setPlayerName(redeemRequest.getPlayer().getPlayerName());
        redeemRequestDto.setGiftName(redeemRequest.getGift().getGiftName());
        return redeemRequestDto;
    }
}
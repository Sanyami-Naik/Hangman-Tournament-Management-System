package com.coditas.HangMan.service.impl;

import com.coditas.HangMan.entity.Gift;
import com.coditas.HangMan.payload.GiftDto;
import com.coditas.HangMan.repository.GiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class GiftImpl {
    @Autowired
    private GiftRepository giftsRepository;


    public String addGifts(Gift gift){
        List<Gift> gifts = giftsRepository.findAll();
        if(gifts.stream().filter(s->s.getGiftName().equals(gift.getGiftName())).collect(Collectors.toList()).size()==0){
            giftsRepository.save(gift);
            return "gifts added";
        }else {
            return "already present";
        }
    }

    public String updateGifts(Gift gift1){
        System.out.println(gift1.getGiftId());
        Gift gift= giftsRepository.findById(gift1.getGiftId()).get();
        gift.setGiftValue(gift1.getGiftValue());
        giftsRepository.save(gift);
        return "value of gifts updated";
    }



    public List<GiftDto> getGiftList()
    {
        return giftsRepository.findAll().stream().filter(gift -> !gift.isDeleted()).map(g->maptoToDto(g)).collect(Collectors.toList());
    }


    public GiftDto maptoToDto(Gift gift)
    {
        GiftDto giftDto=new GiftDto();
        giftDto.setGiftId(gift.getGiftId());
        giftDto.setGiftValue(gift.getGiftValue());
        giftDto.setGiftName(gift.getGiftName());

        return giftDto;
    }


    @CrossOrigin
    public void deleteGift(long giftId) {
        Gift gift1=giftsRepository.findById(giftId).get();
        gift1.setDeleted(true);
        giftsRepository.save(gift1);
    }
}
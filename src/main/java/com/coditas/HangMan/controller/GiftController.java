package com.coditas.HangMan.controller;


import com.coditas.HangMan.entity.Gift;
import com.coditas.HangMan.payload.GiftDto;
import com.coditas.HangMan.service.impl.GiftImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gifts")
@CrossOrigin
public class GiftController {

    @Autowired
    private GiftImpl giftImpl;
//
//    @GetMapping("/getGiftList")
//    public List<Gift> getGiftsList()
//    {
//       return  giftImpl.giftList();
//    }


    @GetMapping("/getGiftList")
    public List<GiftDto>  getGiftList()
    {
        System.out.println("dfghj");
        return  giftImpl.getGiftList();
    }

    @PostMapping("/addGifts")
    public ResponseEntity<HttpStatus>  addGifts(@RequestBody Gift gift) {
        this.giftImpl.addGifts(gift);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PutMapping("/updateGifts")
    public ResponseEntity<HttpStatus> updatePrice(@RequestBody Gift gift) {
        giftImpl.updateGifts(gift);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteGift/{giftId}")
    public ResponseEntity<HttpStatus> deleteGift(@PathVariable long giftId)
    {
        giftImpl.deleteGift(giftId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}

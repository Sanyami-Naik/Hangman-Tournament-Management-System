package com.coditas.HangMan.controller;

import com.coditas.HangMan.entity.RedeemRequest;
import com.coditas.HangMan.payload.AddRequestDto;
import com.coditas.HangMan.payload.GiftRequestHandlingDto;
import com.coditas.HangMan.payload.RedeemRequestDto;
import com.coditas.HangMan.service.impl.RedeemRequestImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/request")
@CrossOrigin

public class RedeemRequestController {
    @Autowired
    private RedeemRequestImpl redeemRequest;

    @PostMapping("/addGiftRequest")
    public ResponseEntity <HttpStatus> addRequest(@RequestBody AddRequestDto addRequestDto){
        this.redeemRequest.addRequest(addRequestDto);
        return new ResponseEntity<>(HttpStatus.OK) ;

    }

    @PostMapping("/updateRequest")
    public ResponseEntity <HttpStatus>updateRequest(@RequestBody GiftRequestHandlingDto giftRequestHandlingDto){
        redeemRequest.updateStatus(giftRequestHandlingDto);
        return new ResponseEntity<>(HttpStatus.OK) ;
    }



    @GetMapping("/getGiftRequests")
    public List<RedeemRequestDto> getPendingGiftRequests()
    {
        return this.redeemRequest.getPendingGiftRequests();
    }

}
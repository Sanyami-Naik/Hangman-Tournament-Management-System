package com.coditas.HangMan.payload;


import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class GiftDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long giftId;
    private String giftName;
    private int  giftValue;

}

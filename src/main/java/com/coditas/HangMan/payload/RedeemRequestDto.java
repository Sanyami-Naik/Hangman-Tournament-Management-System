package com.coditas.HangMan.payload;

import com.coditas.HangMan.entity.Gift;
import com.coditas.HangMan.entity.Player;
import lombok.Data;

import javax.persistence.ManyToOne;


@Data
public class RedeemRequestDto {
    private long redeemRequestId;

    private String redeemRequestStatus="PENDING";

    private long playerId;

    private String playerName;

    private String giftName;

    private long giftId;
}

package com.coditas.HangMan.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftRequestHandlingDto {
    private  long redeemRequestId;
    private  String status;
}
